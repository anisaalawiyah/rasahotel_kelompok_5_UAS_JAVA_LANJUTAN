import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  getCategories,
  getRoom,
  updatePhotoRoom,
  updateRoom,
} from "../utils/fecthApi";
import MaskedInput from "react-text-mask";
import { currencyMask } from "../utils/JSFunction.js";

export default function EditRoom() {
  const [newRoom, setNewRoom] = useState({
    name: "",
    desc: "",
    price: 0,
    category: "",
  });
  const [room, setRoom] = useState({
    name: "",
    desc: "",
    price: 0,
    // category: ""
  });
  const [categories, setCategories] = useState([
    { id: "", name: "", desc: "", saleId: "" },
  ]);
  const [photo, setPhoto] = useState({
    photo: null,
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { roomId } = useParams();

  const handleImageChange = (event) => {
    const imageFile = event.target.files[0];
    setPhoto(imageFile);
  };

  const handleInputChange = (event) => {
    let { name, value } = event.target;
    if (name === "price") {
      value = event.target.value.replaceAll(",", "");
    }
    setNewRoom({ ...newRoom, [name]: value });
  };

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const roomData = await getRoom(roomId);
        setRoom(roomData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchRoom();
  }, [roomId]);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const result = await getCategories();
      setCategories(result);
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await updateRoom(roomId, newRoom)
      .then((res) => {
        setSuccessMessage("Room updated successfully!");
      })
      .catch((e) => {
        console.error(e);
        setErrorMessage(e.message);
      });
    const formData = new FormData();
    formData.append("file", photo);
    await updatePhotoRoom(roomId, formData)
      .then((res) => {
        setSuccessMessage("Room updated successfully!");
      })
      .catch((e) => {
        console.error(e);
        setErrorMessage(e.message);
      });
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <div className="container mt-5 mb-5">
      <h3 className="text-center mb-5 mt-5">Edit Room</h3>
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          {successMessage && (
            <div className="alert alert-success" role="alert">
              {successMessage}
            </div>
          )}
          {errorMessage && (
            <div className="alert alert-danger" role="alert">
              {errorMessage}
            </div>
          )}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="name" className="form-label hotel-color">
                Room Name
              </label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                placeholder={room.name}
                value={room.name}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="desc" className="form-label hotel-color">
                Room Description
              </label>
              <input
                type="text"
                className="form-control"
                id="desc"
                name="desc"
                placeholder={room.desc}
                value={room.desc}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="price" className="form-label hotel-color">
                Room Price
              </label>
              <div className="input-group mb-3">
                <span className="input-group-text">Rp.</span>
                <MaskedInput
                  className="form-control"
                  mask={currencyMask}
                  id="price"
                  name="price"
                  placeholder={room.price}
                  value={room.price}
                  onChange={handleInputChange}
                />
                <span className="input-group-text">.00</span>
              </div>
            </div>
            <div className="mb-3">
              <label htmlFor="category" className="form-label">
                Room Category
              </label>
              <select
                required
                className="form-control"
                id="category"
                name="category"
                onChange={handleInputChange}
              >
                <option value="0">Select Category</option>
                {categories.map((category, i) => (
                  <option key={i} value={category.id}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="mb-3">
              <label htmlFor="photo" className="form-label">
                Room Photo
              </label>
              <input
                required
                className="form-control"
                id="photo"
                name="photo"
                type="file"
                accept="image/jpeg,image/png"
                onChange={(event) => handleImageChange(event)}
              />
            </div>

            <div className="d-grid gap-2 d-md-flex mt-2">
              <Link
                to={"/existing-rooms"}
                className="btn btn-outline-info ml-5"
              >
                back
              </Link>
              <button type="submit" className="btn btn-outline-warning">
                Edit Room
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
