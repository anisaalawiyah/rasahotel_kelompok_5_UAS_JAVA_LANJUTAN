/* eslint-disable react/prop-types */
import { Col } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function Menu({ image, link, title }) {
  return (
    <Col sm>
      <Link className="nav-link p-3 rounded bg-light" to={link}>
        <img
          alt=""
          src={image}
          width="30"
          height="30"
          className="d-inline-block align-top"
        />
        {" " + title}
      </Link>
    </Col>)
}
