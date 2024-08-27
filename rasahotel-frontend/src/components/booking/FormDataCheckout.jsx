import {
  Container,
  Modal,
  Button,
  Form,
  Spinner,
} from "react-bootstrap";
import Header from "../common/Header";
import { useState, useEffect } from "react";
import { getRoom, getUserProfile, saveBooking } from "../utils/fecthApi";
import { useParams, useNavigate } from "react-router-dom";
import moment from "moment";

export default function FormDataCheckout() {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const userId = localStorage.getItem("userId");
  const role = localStorage.getItem("userRole");
  const roomId = useParams().roomId;
  const [errorMessage, setErrorMessage] = useState("");

  const [customer, setCustomer] = useState({
    id: "",
    name: "",
    email: "",
    address: "",
    phoneNumber: "",
  });

  const [room, setRoom] = useState({
    id: "",
    name: "",
    sizeRoom: "",
    price: 0,
    category: {
      id: "",
      name: "",
      description: "",
    },
    booked: false,
    photo: null,
  });

  const [newBooking, setNewBooking] = useState({
    checkInDate: "",
    checkOutDate: "",
    guestName: "",
    guestEmail: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setShowModal(true);
    newBooking.guestEmail = customer.email;
    newBooking.guestName = customer.name;

    await saveBooking(roomId, newBooking)
      .then(() => {
        setShowModal(false);
        setTimeout(navigate("/find-booking", { replace: true }), 3000)
      })
      .catch((e) => {
        setShowModal(false);
        setErrorMessage(e.response.data.responseMessage);
      });
  };

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserProfile(userId);
        setCustomer(userData.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
  }, [userId]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await getRoom(roomId);
        setRoom(response);
      } catch (error) {
        console.error("Error fetching room:", error.message);
      }
    };

    fetchBookings();
  }, [roomId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewBooking({ ...newBooking, [name]: value });
    const checkInDate = moment(newBooking.checkInDate);
    const checkOutDate = moment(newBooking.checkOutDate);
    if (checkInDate.isValid() && checkOutDate.isValid()) {
      console.log("date valid");
    }
  };

  return (
    <Container>
      {role === "USER" ? (
        <>
          <Header title={"Form Data Booking"} />
          <Form onSubmit={handleSubmit}>
            <div className="card p-5 mt-5 bg-light shadow-sm">
              <h4 className="card-title text-center">User Information</h4>
              <div className="card-body">
                <div className="col-md-10 mx-auto">
                  {errorMessage && (
                    <div className="alert alert-danger fade show" role="alert">
                      {" "}
                      {errorMessage}
                    </div>
                  )}
                  <div className="card mb-3 shadow-sm">
                    <div className="row g-0">
                      <div className="col-md-10">
                        <div className="card-body">
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Name:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">{customer.name}</p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Email:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">{customer.email}</p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Address:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">{customer.address}</p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Phone Number:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">
                                {customer.phoneNumber}
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <h4 className="card-title text-center">Room Data</h4>
                  <div className="card mb-3 shadow-sm">
                    <div className="row g-0">
                      <div className="col-md-10">
                        <div className="card-body">
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Name:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">{room.name}</p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Category Room:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">{room.category.name}</p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Price:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">
                                Rp. {Intl.NumberFormat("ID").format(room.price)}
                              </p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Check-In Date:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">
                                <Form.Control
                                  required
                                  type="date"
                                  name="checkInDate"
                                  value={newBooking.checkInDate}
                                  onChange={handleInputChange}
                                  min={moment().format("YYYY-MM-DD")}
                                />
                              </p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group row">
                            <label className="col-md-4 col-form-label fw-bold">
                              Check-Out Date:
                            </label>
                            <div className="col-md-8">
                              <p className="card-text">
                                <Form.Control
                                  required
                                  type="date"
                                  name="checkOutDate"
                                  value={newBooking.checkOutDate}
                                  onChange={handleInputChange}
                                  min={moment().format("YYYY-MM-DD")}
                                />
                              </p>
                            </div>
                          </div>
                          <hr />
                          <div className="form-group">
                            <Button type="submit">Book Now</Button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Form>
          {/* Modal */}
          <Modal
            backdrop="static"
            size="lg"
            show={showModal}
            arialabelledby="container-modal-title-vcenter"
            centered
          >
            <Modal.Header>
              <Modal.Title>Saving Booking Data</Modal.Title>
            </Modal.Header>
            <Modal.Body className="text-center">
              <Spinner role="status"></Spinner>
            </Modal.Body>
          </Modal>
        </>
      ) : (
        <div className="alert alert-danger" role="alert">
          Youre not user
        </div>
      )}
    </Container>
  );
}
