package com.example.authapp.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import com.example.authapp.data.AppDatabase;
import com.example.authapp.model.User;
import com.example.authapp.util.PasswordUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AuthManager {
    private final AppDatabase db;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Context context;
    private final SharedPreferences prefs;

    public AuthManager(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
        this.prefs = context.getSharedPreferences("auth_session", Context.MODE_PRIVATE);
    }

    public interface Callback {
        void onSuccess();
        void onError(String message);
    }

    public void register(String email, String password, String confirm, Callback callback) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            callback.onError("Некорректный email");
            return;
        }
        if (password.length() < 6) {
            callback.onError("Пароль минимум 6 символов");
            return;
        }
        if (!password.equals(confirm)) {
            callback.onError("Пароли не совпадают");
            return;
        }

        executor.execute(() -> {
            try {
                User existing = db.userDao().getUserByEmail(email);
                if (existing != null) {
                    mainHandler.post(() -> callback.onError("Пользователь с таким email уже существует"));
                    return;
                }
                String hash = PasswordUtil.hashPassword(password);
                db.userDao().insertUser(new User(email, hash));
                mainHandler.post(callback::onSuccess);
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError("Ошибка БД: " + e.getMessage()));
            }
        });
    }

    public void login(String email, String password, Callback callback) {
        executor.execute(() -> {
            try {
                User user = db.userDao().getUserByEmail(email);
                if (user == null || !PasswordUtil.verifyPassword(password, user.passwordHash)) {
                    mainHandler.post(() -> callback.onError("Неверный email или пароль"));
                    return;
                }
                saveSession(email);
                mainHandler.post(callback::onSuccess);
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError("Ошибка входа: " + e.getMessage()));
            }
        });
    }

    private void saveSession(String email) {
        prefs.edit().putBoolean("is_logged_in", true).putString("user_email", email).apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean("is_logged_in", false);
    }

    public String getUserEmail() {
        return prefs.getString("user_email", null);
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}