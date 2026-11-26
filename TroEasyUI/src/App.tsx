import { RouterProvider } from "react-router-dom";
import { QueryClientProvider } from "@tanstack/react-query";
import { Toaster } from "react-hot-toast";
import { queryClient } from "./hooks/queryClient.ts";
import router from "./routers/router.tsx";

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router} />
            <Toaster position="top-center" toastOptions={{ duration: 3000 }} />
        </QueryClientProvider>
    );
}

export default App;
