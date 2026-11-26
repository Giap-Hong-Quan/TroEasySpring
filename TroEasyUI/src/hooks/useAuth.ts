import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useNavigate, useLocation } from "react-router-dom";
import toast from "react-hot-toast";
import { login, logout, getCurrentUser } from "../services/authService.ts";

export const AUTH_KEYS = {
    currentUser: ["currentUser"] as const,
};

// Hook đăng nhập với useMutation
export const useLogin = () => {
    const queryClient = useQueryClient();
    const navigate = useNavigate();
    const location = useLocation();

    return useMutation({
        mutationFn: login,
        onSuccess: (data) => {
            queryClient.setQueryData(AUTH_KEYS.currentUser, data);
            toast.success("Đăng nhập thành công!");
            const from = location.state?.from?.pathname || "/";
            navigate(from, { replace: true });
        },
        onError: (error: any) => {
            const message = error?.response?.data?.message || error?.message || "Đăng nhập thất bại. Vui lòng thử lại.";
            toast.error(message);
        },
    });
};

// Hook lấy thông tin người dùng hiện tại với useQuery
export const useCurrentUser = () => {
    const hasToken = typeof window !== "undefined" && !!localStorage.getItem("accessToken");

    return useQuery({
        queryKey: AUTH_KEYS.currentUser,
        queryFn: getCurrentUser,
        enabled: hasToken,
        staleTime: 5 * 60 * 1000, // 5 phút cache
        retry: 1,
    });
};

// Hook đăng xuất với useMutation
export const useLogout = () => {
    const queryClient = useQueryClient();
    const navigate = useNavigate();

    return useMutation({
        mutationFn: async () => {
            logout();
        },
        onSuccess: () => {
            queryClient.removeQueries({ queryKey: AUTH_KEYS.currentUser });
            queryClient.clear();
            toast.success("Đã đăng xuất thành công.");
            navigate("/login", { replace: true });
        },
    });
};
