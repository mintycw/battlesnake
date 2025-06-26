import { Link } from "react-router-dom";
import Login from "./Login";

export default function Header() {
	return (
		<header className="fixed top-0 left-0 right-0 bg-slate-800 h-14 flex items-center justify-between px-6 shadow-md z-50">
			{" "}
			<nav className="flex space-x-6">
				<Link
					to="/"
					className="text-white font-semibold text-lg hover:text-pink-500 transition-colors duration-200"
				>
					Battlesnake Dashboard
				</Link>
				<Link
					to="/customization"
					className="text-white font-semibold text-lg hover:text-pink-500 transition-colors duration-200"
				>
					Customization
				</Link>
				<Link
					to="/history"
					className="text-white font-semibold text-lg hover:text-pink-500 transition-colors duration-200"
				>
					History
				</Link>
			</nav>
			<div>
				<Login />
			</div>
		</header>
	);
}
