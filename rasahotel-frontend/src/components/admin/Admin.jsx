/* eslint-disable react-hooks/rules-of-hooks */
import { Row } from "react-bootstrap";
import Menu from "./Menu";
import { useNavigate } from "react-router";
import { useEffect } from "react";

export default function Admin() {
  const userRole = localStorage.getItem("userRole");
  const navigate = useNavigate();
  const redirectUrl = "/";

  if (userRole !== "ADMIN")
    useEffect(() => {
      navigate(redirectUrl, { replace: true });
    });
  else
    return (
      <>
        <section className="container mt-5">
          <h1>Welcome to Admin Panel</h1>
          <hr />
          <Row className="m-5">
            <Menu
              image={"/vite.svg"}
              link={"/existing-rooms"}
              title={"Manage Rooms"}
            />
            <Menu
              image={"/vite.svg"}
              link={"/existing-categories"}
              title={"Manage Category"}
            />
            <Menu
              image={"/vite.svg"}
              link={"/existing-sales"}
              title={"Manage Sales"}
            />
          </Row>
          <Row className="m-5">
            <Menu
              image={"/vite.svg"}
              link={"/report"}
              title={"Report"}
            />
          </Row>
        </section>
      </>
    );
}
