import { useState } from "react";
import { addSale } from "../utils/fecthApi";
import { Link } from "react-router-dom";
import MaskedInput from "react-text-mask";
import { currencyMask } from "../utils/JSFunction.js";

export default function AddSale() {
  const [newSale, setNewSale] = useState({
    name: "",
    price: 0
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleRoomInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    if (name === "price") {
        value = e.target.value.replaceAll(",","");
    }
    setNewSale({ ...newSale, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await addSale(newSale);
      if (res.data.success) {
        setSuccessMessage("A new sale was added successfully !");
        setNewSale({ name: "", price: 0});
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new sale");
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
            <h2 className="mt-5 mb-2">Add a New Sale</h2>
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
                  Sale Name
                </label>
                <input
                  required
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  value={newSale.name}
                  onChange={(e)=>{handleRoomInputChange(e)}}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="price" className="form-label">
                  Sale Price
                </label>
                <div className="input-group mb-3">
                  <span className="input-group-text">Rp.</span>
                  <MaskedInput
                    className="form-control"
                    mask={currencyMask}
                    id="price"
                    name="price"
                    value={newSale.price}
                    onChange={handleRoomInputChange}
                  />
                  <span className="input-group-text">.00</span>
                </div>
              </div>

              <div className="d-grid gap-2 d-md-flex mt-2">
                <Link to={"/existing-sales"} className="btn btn-outline-info">
                  Existing sales
                </Link>
                <button type="submit" className="btn btn-outline-primary ml-5">
                  Save Sale
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
}
