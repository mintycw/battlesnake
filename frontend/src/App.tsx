import { Route, Routes } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import Customization from "./pages/Customization";
import Header from "./components/Header";
import { AuthProvider } from "./context/AuthContext";
import GameDetails from "./components/history/GameDetails";
import History from "./pages/History";

function App() {
	return (
		<AuthProvider>
			<div className="flex flex-col min-h-screen">
				<Header />

				<main className="h-[calc(100vh-3.5rem)] mt-14 overflow-y-auto">
					<div className="flex min-h-full">
						<Routes>
							<Route index element={<Dashboard />} />
							<Route
								path="/customization"
								element={<Customization />}
							/>
							<Route path="history" element={<History />}>
								<Route path=":id" element={<GameDetails />} />
							</Route>
						</Routes>
					</div>
				</main>
			</div>
		</AuthProvider>
	);
}

export default App;
