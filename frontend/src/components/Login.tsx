import React, { useState } from "react";
import { login, logout } from "../lib/api/authService";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function Login() {
	const { authenticated, user } = useAuth();

	const [username, setUsername] = useState<string>("");
	const [password, setPassword] = useState<string>("");

	const navigate = useNavigate();

	function handleLogin(event: React.FormEvent) {
		event.preventDefault();

		login(username, password).then((data) => {
			console.log("Login successful:", data);
			navigate(0);
		});
	}

	function handleLogout(): void {
		logout().then(() => {
			console.log("Logout successful");
			navigate(0);
		});
	}

	return !authenticated ? (
		<form onSubmit={handleLogin} className="flex items-center space-x-2">
			<input
				type="text"
				placeholder="username"
				onChange={(e) => setUsername(e.target.value)}
				value={username}
				className="px-2 py-1 rounded border border-gray-300 outline-0 text-slate-100 w-40 text-sm"
			/>
			<input
				type="password"
				placeholder="password"
				onChange={(e) => setPassword(e.target.value)}
				value={password}
				className="px-2 py-1 rounded border border-gray-300 outline-0 text-slate-100 w-40 text-sm"
			/>
			<button className="px-4 py-1 bg-slate-500 text-white rounded hover:brightness-90 transition duration-200">
				Login
			</button>
		</form>
	) : (
		<div className="text-slate-100">
			Welcome, <span className="font-bold">{user}</span>!{" "}
			<button
				className="text-pink-500 hover:underline"
				onClick={handleLogout}
			>
				Logout
			</button>
		</div>
	);
}
