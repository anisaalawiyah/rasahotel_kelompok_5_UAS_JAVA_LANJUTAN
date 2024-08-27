import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getSale, updateSale } from "../utils/fecthApi.js";
import MaskedInput from "react-text-mask";
import { currencyMask } from "../utils/JSFunction.js";

export default function EditSale() {
  const [sale, setSale] = useState({
    name: "",
    price: 0
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { saleId } = useParams();

  const handleInputChange = (event) => {
    let { name, value } = event.target;
    if (name === "price") {
      value = event.target.value.replaceAll(",","");
  }
  setSale({ ...sale, [name]: value });
  };

  useEffect(() => {
    const fetchSale = async () => {
      try {
        const roomData = await getSale(saleId)
        setSale(roomData)
      } catch (error) {
        console.error(error);
      }
    };

    fetchSale();
  }, [saleId]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await updateSale(saleId, sale);
      if (response.success) {
        setSuccessMessage("Sale updated successfully!");
        const updatedSaleData = await getSale(saleId);
        setSale(updatedSaleData);
        setErrorMessage("");
      } else {
        setErrorMessage("Error updating sale");
      }
    } catch (error) {
      console.error(error);
      setErrorMessage(error.message);
    }
    setTimeout(() => {
			setSuccessMessage("")
			setErrorMessage("")
		}, 3000)
  };

  return (
    <div className="container mt-5 mb-5">
      <h3 className="text-center mb-5 mt-5">Edit Sale</h3>
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
          <form onSubmit={handleSubmit} encType="multipart/form-data">
            <div className="mb-3">
              <label htmlFor="name" className="form-label hotel-color">
                Sale Name
              </label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                value={sale.name}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="price" className="form-label hotel-color">
                Sale Price
              </label>
              <div className="input-group mb-3">
                  <span className="input-group-text">Rp.</span>
                  <MaskedInput
                    className="form-control"
                    mask={currencyMask}
                    id="price"
                    name="price"
                    value={sale.price}
                    onChange={handleInputChange}
                  />
                  <span className="input-group-text">.00</span>
            </div>
            </div>

            <div className="d-grid gap-2 d-md-flex mt-2">
              <Link
                to={"/existing-sales"}
                className="btn btn-outline-info ml-5"
              >
                back
              </Link>
              <button type="submit" className="btn btn-outline-warning">
                Edit Sale
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
