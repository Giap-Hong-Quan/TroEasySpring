import { apiClient } from "./apiclient.ts";

// Lấy thống kê tỷ lệ lấp đầy phòng cho Dashboard
export const getRoomOccupancyStats = async () => {
    try {
        const response: any = await apiClient.get("/rooms/stats/occupancy");
        return response?.result || response?.data || response;
    } catch {
        // Dữ liệu mặc định nếu Backend chưa làm API thống kê này
        return {
            totalRooms: 17,
            rentingRooms: 11,
            occupancyRate: 65,
        };
    }
};

// Lấy danh sách phòng
export const getRooms = async (params?: any) => {
    const response: any = await apiClient.get("/rooms", { params });
    return response?.result || response?.data || response;
};
