/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */
import { Card, Col } from "react-bootstrap";
import { FaCalendar } from "react-icons/fa";
import { Link } from "react-router-dom";

export default function RoomCard({ key, room }) {
  return (
    <Col key={key} className="mb-4" xs={12}>
      <Card>
        <Card.Body className="d-flex flex-nowrap align-items-center">
          <div className="flex-shrink-0 mr-3 mb-3 mb-0">
            <Link to={`/book-room/${room.id}`}>
              <Card.Img
                variant="top"
                src={`data:image/png;base64, ${room.photo}`}
                alt="Room Photo"
                style={{ width: "100%", maxWidth: "200px", height: "100%",  }}
              />
            </Link>
          </div>
          <div className="flex-grow-1 ml-3 px-5">
            <Card.Title className="hotel-color">{room.name}</Card.Title>
            <Card.Title className="room-price">
              Rp. {Intl.NumberFormat("ID").format(room.price)} / night
            </Card.Title>
            <Card.Text className="w-50">
              {room.desc}
            </Card.Text>
          </div>
          <div className="flex-shrink-0 mt-3 justify-content-between">
            <Link to={`/book-room/${room.id}`} className="btn btn-primary">
              <FaCalendar />{" "}Book Now
            </Link>
          </div>
        </Card.Body>
      </Card>
    </Col>
  );
}
