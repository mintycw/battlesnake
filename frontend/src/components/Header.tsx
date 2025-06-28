import { Link } from "react-router-dom";
import Login from "./Login";
import { useAuth } from "../context/AuthContext";
import { useState } from "react";
import { MdMenu, MdClose } from "react-icons/md";

export default function Header() {
	const { user, authenticated } = useAuth();
	const [menuOpen, setMenuOpen] = useState(false);

	return (
		<header className="fixed top-0 left-0 right-0 bg-slate-800 h-14 flex items-center justify-between px-4 sm:px-6 shadow-md z-50">
			<div className="flex items-center justify-between w-full">
				{user && authenticated && (
					<>
						<button
							onClick={() => setMenuOpen(!menuOpen)}
							className="text-white sm:hidden"
							aria-label="Toggle menu"
						>
							{menuOpen ? (
								<MdClose size={24} />
							) : (
								<MdMenu size={24} />
							)}
						</button>

						<nav
							className={`${
								menuOpen ? "flex" : "hidden"
							} absolute top-14 left-0 w-full bg-slate-800 flex-col space-y-4 px-6 py-4 sm:static sm:flex sm:flex-row sm:space-y-0 sm:space-x-6 sm:top-0 sm:w-auto sm:py-0 sm:px-0`}
						>
							<Link
								to="/"
								className="text-white font-semibold text-lg hover:text-pink-500 transition-colors duration-200"
								onClick={() => setMenuOpen(false)}
							>
								Battlesnake Dashboard
							</Link>
							<Link
								to="/customization"
								className="text-white font-semibold text-lg hover:text-pink-500 transition-colors duration-200"
								onClick={() => setMenuOpen(false)}
							>
								Customization
							</Link>
							<Link
								to="/history"
								className="text-white font-semibold text-lg hover:text-pink-500 transition-colors duration-200"
								onClick={() => setMenuOpen(false)}
							>
								History
							</Link>
						</nav>
					</>
				)}
				<div className="lg:ml-auto sm:ml-0">
					<Login />
				</div>
			</div>
		</header>
	);
}
