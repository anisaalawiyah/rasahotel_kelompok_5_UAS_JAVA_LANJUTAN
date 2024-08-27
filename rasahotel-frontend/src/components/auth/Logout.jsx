import { useContext } from "react";
import { AuthContext } from "./AuthProvider";
import { Link, useNavigate } from "react-router-dom";

export default function Logout({ handleAccountClick }) {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();
  const userRole = localStorage.getItem("userRole");

  const handleLogout = () => {
    handleAccountClick();
    auth.handleLogout();
    navigate("/", { state: { message: " You have been logged out!" } });
  };

  return (
    <>
      {userRole === "USER" && (
        <>
          <li>
            <Link
              onClick={handleAccountClick}
              className="dropdown-item"
              to={"/profile"}
            >
              Profile
            </Link>
          </li>
          <li>
            <hr className="dropdown-divider" />
          </li>
        </>
      )}
      <button className="dropdown-item" onClick={handleLogout}>
        Logout
      </button>
    </>
  );
}
