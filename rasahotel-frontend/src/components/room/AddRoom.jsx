import { useState } from "react";
import { addRoom, getCategories } from "../utils/fecthApi";
import { Link } from "react-router-dom";
import MaskedInput from "react-text-mask";
import { currencyMask } from "../utils/JSFunction.js";
import { useEffect } from "react";

export default function AddRoom() {
  const [newRoom, setNewRoom] = useState({
    name: "",
    description: "",
    price: 0,
    categoryId: "",
  });

  const [categories, setCategories] = useState([{ id: "", name: "", desc: "", saleId: "" }])

  useEffect(() => {
		fetchCategories()
	}, [])

	const fetchCategories = async () => {
		try {
			const result = await getCategories()
			setCategories(result)
		} catch (error) {
			setErrorMessage(error.message)
		}
	}


  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleRoomInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    if (name === "price") {
      value = e.target.value.replaceAll(",", "");
    }
    setNewRoom({ ...newRoom, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await addRoom(newRoom);
      if (res.success) {
        setSuccessMessage("A new room was added successfully !");
        setNewRoom({ name: "", size: "", description:"", price: "", categoryId: "" });
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new room");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <>
      <section className="container mt-5 mb-5">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <h2 className="mt-5 mb-2">Add a New Room</h2>
            {successMessage && (
              <div className="alert alert-success fade show">
                {" "}
                {successMessage}
              </div>
            )}

            {errorMessage && (
              <div className="alert alert-danger fade show">
                {" "}
                {errorMessage}
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">
                  Room Name
                </label>
                <input
                  required
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  value={newRoom.name}
                  onChange={(e) => {
                    handleRoomInputChange(e);
                  }}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="description" className="form-label">
                  Room Description
                </label>
                <input
                  required
                  type="text"
                  className="form-control"
                  id="description"
                  name="description"
                  value={newRoom.description}
                  onChange={(e) => {
                    handleRoomInputChange(e);
                  }}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="price" className="form-label">
                  Room Price
                </label>
                <div className="input-group mb-3">
                  <span className="input-group-text">Rp.</span>
                  <MaskedInput
                    className="form-control"
                    mask={currencyMask}
                    id="price"
                    name="price"
                    value={newRoom.price}
                    onChange={handleRoomInputChange}
                  />
                  <span className="input-group-text">.00</span>
                </div>
              </div>
              <div className="mb-3">
                <label htmlFor="categoryId" className="form-label">
                  Room Category
                </label>
                <select
                  required
                  className="form-control"
                  id="categoryId"
                  name="categoryId"
                  onChange={handleRoomInputChange}
                >
                  <option value="0">Select Category</option>
                  {categories.map((category, i) => (
                    <option key={i} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="d-grid gap-2 d-md-flex mt-2">
                <Link to={"/existing-rooms"} className="btn btn-outline-info">
                  Existing rooms
                </Link>
                <button type="submit" className="btn btn-outline-primary ml-5">
                  Save Room
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
}
