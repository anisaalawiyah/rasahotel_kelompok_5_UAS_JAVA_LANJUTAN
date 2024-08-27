import { useState } from "react";
import { NavLink, Link } from "react-router-dom";
import Logout from "../auth/Logout";
import { useEffect } from "react";
import { Button } from "react-bootstrap";

export default function NavigationBar() {
  const [showAccount, setShowAccount] = useState(false);
  const isLoggedIn = localStorage.getItem("token");
  const userRole = localStorage.getItem("userRole");
  const [scrollPosition, setScrollPosition] = useState(0);

  useEffect(() => {
    const handleScroll = () => {
      const position = window.scrollY;
      setScrollPosition(position);
    };

    window.addEventListener("scroll", handleScroll, { passive: true });

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  const headerStyle = {
    backgroundColor: `rgba(233, 165, 241, ${scrollPosition > 40 ? 1 : scrollPosition/100})`, // Transparan saat scroll > 100px
    transition: "background-color 0.5s ease", // Animasi perubahan warna
    color: "white",
  };

  function handleAccountClick() {
    setShowAccount(!showAccount);
  }

  return (
    <nav
      className={
        scrollPosition > 40 || window.location.pathname === "/"
          ? "navbar navbar-expand-lg px-5 fixed-top shadow-sm"
          : "navbar navbar-expand-lg px-5 sticky-top shadow-sm"
      }
      style={
        window.location.pathname === "/"
          ? headerStyle
          : { backgroundColor: "white" }
      }
    >
      <div className="container-fluid">
        {scrollPosition > 40 || window.location.pathname !== "/" ? (
          <Link to={"/"} className="navbar-brand">
            <img src={"./RasaHotel.png"} alt="" height="40" />
          </Link>
        ) : (
          <h1 className="bar">Rasa Hotel</h1>
        )}

        <Button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarScroll"
          aria-controls="navbarScroll"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </Button>

        <div className="collapse navbar-collapse" id="navbarScroll">
          {scrollPosition > 40 || window.location.pathname !== "/" ? (
            <ul className="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll">
              <li className="nav-item">
                <NavLink
                  className="nav-link"
                  aria-current="page"
                  to={"/browse-all-rooms"}
                >
                  ROOMS
                </NavLink>
              </li>

              {isLoggedIn && userRole === "ADMIN" && (
                <li className="nav-item">
                  <NavLink
                    className="nav-link"
                    aria-current="page"
                    to={"/admin"}
                  >
                    ADMIN
                  </NavLink>
                </li>
              )}
            </ul>
          ) : (
            <ul className="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"></ul>
          )}

          <ul className="d-flex navbar-nav">
            {isLoggedIn && userRole === "USER" && (
              <li className="nav-item">
                <NavLink className="nav-link" to={"/find-booking"}>
                  Find my booking
                </NavLink>
              </li>
            )}

            <li className="nav-item dropdown">
              {scrollPosition > 40 || window.location.pathname !== "/" ? (
                <a
                  className={`nav-link dropdown-toggle ${
                    showAccount ? "show" : ""
                  }`}
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  onClick={handleAccountClick}
                >
                  {" "}
                  {isLoggedIn ? localStorage.getItem("userId").substring(0,2)+"***@***"+localStorage.getItem("userId").split("@")[1].substring(4,localStorage.getItem("userId").length) : "Account"}
                </a>
              ):("")}

              <ul
                className={`dropdown-menu ${showAccount ? "show" : ""}`}
                aria-labelledby="navbarDropdown"
              >
                {isLoggedIn ? (
                  <Logout handleAccountClick={handleAccountClick} />
                ) : (
                  <>
                    <li className="w-10">
                      <Link
                        className="dropdown-item"
                        to={"/login"}
                        onClick={handleAccountClick}
                      >
                        Login
                      </Link>
                    </li>
                    <li className="w-10">
                      <Link
                        className="dropdown-item"
                        to={"/register"}
                        onClick={handleAccountClick}
                      >
                        Register
                      </Link>
                    </li>
                  </>
                )}
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
