import { apiClient } from "./apiclient.ts";

export const login = async ({ username, password }: { username: string; password: string }) => {
    const response: any = await apiClient.post("/auth/login", { username, password });
    const authData = response?.result || response?.data?.result;

    if (!authData?.accessToken) {
        throw new Error("API không trả về access token.");
    }

    localStorage.setItem("accessToken", authData.accessToken);
    return authData;
};

export const getCurrentUser = async () => {
    const response: any = await apiClient.get("/auth/me");
    return response?.result || response?.data || response;
};

export const logout = () => {
    localStorage.removeItem("accessToken");
};
