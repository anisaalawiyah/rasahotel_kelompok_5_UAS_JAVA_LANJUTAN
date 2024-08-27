/* eslint-disable react/prop-types */
import { Navigate, useLocation } from "react-router-dom"

export default function RequireAuth({children}) {

    const user = localStorage.getItem("userId")
	const location = useLocation()
	if (user===null) {
		return <Navigate to="/login" state={{ path: location.pathname }} />
	}
	return children
}