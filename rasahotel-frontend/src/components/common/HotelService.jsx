import { Container, Row, Col, Card } from "react-bootstrap";
import Header from "./Header";
import {
  FaCocktail,
  FaParking,
  FaSnowflake,
  FaTshirt,
  FaUtensils,
  FaWifi,
} from "react-icons/fa";

export default function HotelService() {
  const services = [
    {
      title: "Wi-Fi",
      icon: <FaWifi />,
      text: "Stay connected with high-speed internet access.",
    },
    {
      title: "Breakfast",
      icon: <FaUtensils />,
      text: "Start your day with a delicious breakfast buffet.",
    },
    {
      title: "Laundry",
      icon: <FaTshirt />,
      text: "Keep your clothes clean and fresh with our laundry service.",
    },
    {
      title: "Mini Bar",
      icon: <FaCocktail />,
      text: "Enjoy a refreshing drink or snack from our in-room mini-bar.",
    },
    {
      title: "Parking",
      icon: <FaParking />,
      text: "Park your car conveniently in our on-site parking lot.",
    },
    {
      title: "Air Conditioner",
      icon: <FaSnowflake />,
      text: "Stay cool and comfortable with our air conditioning system.",
    },
  ];

  return (
    <>
      <Header title={"Our Services"} />
      <Container className="pb-4">
        <Row xs={1} md={2} lg={3} className="g-4 mt-2">
          {services.map((service, i)=>(
            <Col key={i}>
            <Card>
              <Card.Body>
                <Card.Title className="text-center hotel-color">
                  {service.icon} {service.title}
                </Card.Title>
                <Card.Text className="text-center">
                  {service.text}
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
          ))}
        </Row>
      </Container>
      <hr />
    </>
  );
}
