import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { useAuth } from "./AuthProvider"
import { login } from "../utils/fecthApi"

export default function Login() {

    const [errorMessage, setErrorMessage] = useState("")
	const [user, setUser] = useState({
		username: "",
		password: ""
	})

	const navigate = useNavigate()
	const redirectUrl = "/"
	const auth = useAuth()

	const handleInputChange = (e) => {
		setUser({ ...user, [e.target.name]: e.target.value })
	}

	const handleSubmit = async (e) => {
		e.preventDefault()
		const success = await login(user)
		if (success) {
			const token = success.token
			auth.handleLogin(token)
			navigate(redirectUrl, { replace: true })
			window.location.reload()
		} else {
			setErrorMessage("Invalid username or password. Please try again.")
		}
		setTimeout(() => {
			setErrorMessage("")
		}, 4000)
	}

    return (
		<section className="container col-6 mt-5 mb-5">
			{errorMessage && <p className="alert alert-danger">{errorMessage}</p>}
			<h2>Login</h2>
			<form onSubmit={handleSubmit}>
				<div className="row mb-3">
					<label htmlFor="email" className="col-sm-2 col-form-label">
						Username
					</label>
					<div>
						<input
							id="email"
							name="username"
							type="email"
							className="form-control"
							value={user.username}
							onChange={handleInputChange}
						/>
					</div>
				</div>

				<div className="row mb-3">
					<label htmlFor="password" className="col-sm-2 col-form-label">
						Password
					</label>
					<div>
						<input
							id="password"
							name="password"
							type="password"
							className="form-control"
							value={user.password}
							onChange={handleInputChange}
						/>
					</div>
				</div>

				<div className="mb-3">
					<button type="submit" className="btn btn-hotel" style={{ marginRight: "10px" }}>
						Login
					</button>
					<span style={{ marginLeft: "10px" }}>
						{"Don't"} have an account yet?<Link to={"/register"}> Register</Link>
					</span>
				</div>
			</form>
		</section>
	)
}