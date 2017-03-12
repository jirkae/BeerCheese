package edu.vse.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CallContext {

    private static final ThreadLocal<CallContext> map = ThreadLocal.withInitial(() -> new CallContext());

    private String username;

    private Integer userId;

    private List<String> roles;

    public CallContext() {
    }

    public CallContext(String username, Integer userId, List<String> roles) {
        this.username = username;
        this.userId = userId;
        this.roles = roles;
    }

    public Optional<String> getUsername() {
        if (username == null) {
            return Optional.empty();
        } else {
            return Optional.of(username);
        }
    }

    public Optional<Integer> getUserId() {
        if (userId == null) {
            return Optional.empty();
        } else {
            return Optional.of(userId);
        }
    }

    public List<String> getRoles() {
        if (roles == null) {
            return new ArrayList<>();
        } else {
            return roles;
        }
    }

    public static void setContext(CallContext callContext) {
        map.set(callContext);
    }

    public static CallContext getContext() {
        return map.get();
    }

    public static void clearContext() {
        map.remove();
    }

    public static boolean isAdmin() {
        return map.get().getRoles().contains("admin");
    }
}
