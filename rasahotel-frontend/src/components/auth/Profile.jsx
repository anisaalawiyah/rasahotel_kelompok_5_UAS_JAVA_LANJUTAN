import { useEffect, useState } from "react";
import {
  deleteUser,
  getBookingsByUserId,
  getUserProfile,
} from "../utils/fecthApi";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import RequireAuth from "./RequireAuth";
import { FaExclamationTriangle } from "react-icons/fa";

export default function Profile() {
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");

  const [user, setUser] = useState({
    id: "",
    name: "",
    email: "",
    address: "",
    phoneNumber: "",
  });

  const [bookings, setBookings] = useState([
    {
      id: "",
      room: { id: "", name: "", price: "", roomSize: "", photo: null },
      checkInDate: "",
      checkOutDate: "",
      guestFullName: "",
      guestEmail: "",
      bookingConfirmationCode: "",
      users: {
        id: "",
        username: "",
        password: "",
        roles: { id: "", roleName: "", roleDesc: "" },
      },
    },
  ]);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserProfile(userId);
        setUser(userData.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
  }, [userId]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await getBookingsByUserId(userId);
        setBookings(response);
      } catch (error) {
        console.error("Error fetching bookings:", error.message);
        setBookings([])
        // setErrorMessage(error.message);
      }
    };

    fetchBookings();
  }, [userId]);

  const handleDeleteAccount = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to delete your account? This action cannot be undone."
    );
    if (confirmed) {
      await deleteUser(userId)
        .then((response) => {
          setMessage(response.data);
          localStorage.removeItem("token");
          localStorage.removeItem("userId");
          localStorage.removeItem("userRole");
          navigate("/");
          window.location.reload();
        })
        .catch((error) => {
          setErrorMessage(error.data);
        });
    }
  };


  return (
    <div className="container">
      <RequireAuth>
        {errorMessage && <p className="text-danger"><FaExclamationTriangle />{errorMessage}</p>}
        {message && <p className="text-danger">{message}</p>}
        {user ? (
          <div className="card p-5 mt-5 bg-light">
            <h4 className="card-title text-center">User Information</h4>
            <div className="card-body">
              <div className="col-md-10 mx-auto">
                <div className="card mb-3 shadow-sm">
                  <div className="row g-0">
                    <div className="col-md-2">
                      <div className="d-flex justify-content-center align-items-center m-2">
                        <img
                          src="https://themindfulaimanifesto.org/wp-content/uploads/2020/09/male-placeholder-image.jpeg"
                          alt="Profile"
                          className="rounded-circle"
                          style={{
                            width: "130px",
                            height: "130px",
                            objectFit: "cover",
                          }}
                        />
                      </div>
                    </div>

                    <div className="col-md-10">
                      <div className="card-body">

                        <div className="form-group row">
                          <label className="col-md-2 col-form-label fw-bold">
                            Name:
                          </label>
                          <div className="col-md-10">
                            <p className="card-text">{user.name}</p>
                          </div>
                        </div>
                        <hr />

                        <div className="form-group row">
                          <label className="col-md-2 col-form-label fw-bold">
                            Email:
                          </label>
                          <div className="col-md-10">
                            <p className="card-text">{user.email}</p>
                          </div>
                        </div>
                        <hr />

                        <div className="form-group row">
                          <label className="col-md-2 col-form-label fw-bold">
                            Address:
                          </label>
                          <div className="col-md-10">
                            <p className="card-text">{user.address}</p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <h4 className="card-title text-center">Booking History</h4>

                {bookings.length > 0 ? (
                  <table className="table table-bordered table-hover shadow-sm">
                    <thead>
                      <tr>
                        <th scope="col">No</th>
                        <th scope="col">Room ID</th>
                        <th scope="col">Room Name</th>
                        <th scope="col">Check In Date</th>
                        <th scope="col">Check Out Date</th>
                        <th scope="col">Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {bookings.map((booking, index) => (
                        <tr key={index}>
                          <td>{index + 1}</td>
                          <td>{booking.bookingId}</td>
                          <td>{booking.room.name}</td>
                          <td>
                            {moment(booking.checkInDate)
                              .subtract(1, "month")
                              .format("MMM Do, YYYY")}
                          </td>
                          <td>
                            {moment(booking.checkOutDate)
                              .subtract(1, "month")
                              .format("MMM Do, YYYY")}
                          </td>
                          {booking.paymentRequired ? (

                            <td className="text-danger">Pending</td>
                            ):(
                            <td className="text-success">Success</td>

                          )}
                        </tr>
                      ))}
                    </tbody>
                  </table>
                ) : (
                  <p>You have not made any bookings yet.</p>
                )}

                <div className="d-flex justify-content-center">
                  <div className="mx-2">
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={handleDeleteAccount}
                    >
                      Close account
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ) : (
          <p>Loading user data...</p>
        )}
      </RequireAuth>
    </div>
  );
}
