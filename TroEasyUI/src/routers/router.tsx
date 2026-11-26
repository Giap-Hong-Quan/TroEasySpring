import { createBrowserRouter, Navigate } from "react-router-dom";
import ProtectedRoute from "../libs/ProtectedRoute.tsx";
import MainLayout from "../layout/index.tsx";
import DashboardPage from "../pages/DashboardPage.tsx";
import LoginPage from "../pages/auth/LoginPage.tsx";

const PlaceholderPage = ({ title }: { title: string }) => (
    <div className="flex min-h-[400px] flex-col items-center justify-center rounded-2xl border border-slate-100 bg-white p-12 text-center shadow-sm">
        <div className="mb-4 flex h-16 w-16 items-center justify-center rounded-2xl bg-indigo-50 text-2xl">
            🏗️
        </div>
        <h2 className="text-xl font-bold text-slate-800">{title}</h2>
        <p className="mt-2 text-sm text-slate-500">Tính năng đang được phát triển, vui lòng quay lại sau.</p>
    </div>
);

const router = createBrowserRouter([
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        path: "/",
        element: <ProtectedRoute />,
        children: [
            {
                element: <MainLayout />,
                children: [
                    {
                        index: true,
                        element: <DashboardPage />,
                    },
                    {
                        path: "rooms",
                        element: <PlaceholderPage title="Sơ đồ phòng" />,
                    },
                    {
                        path: "tenants",
                        element: <PlaceholderPage title="Khách thuê" />,
                    },
                    {
                        path: "contracts",
                        element: <PlaceholderPage title="Hợp đồng" />,
                    },
                    {
                        path: "utilities",
                        element: <PlaceholderPage title="Điện & nước" />,
                    },
                    {
                        path: "invoices",
                        element: <PlaceholderPage title="Hóa đơn & thu tiền" />,
                    },
                    {
                        path: "maintenance",
                        element: <PlaceholderPage title="Bảo trì" />,
                    },
                ],
            },
        ],
    },
    {
        path: "*",
        element: <Navigate to="/" replace />,
    },
]);

export default router;
