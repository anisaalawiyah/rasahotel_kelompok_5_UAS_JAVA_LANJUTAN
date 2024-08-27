import { useState, useEffect } from "react";
import { addCategory, getSales } from "../utils/fecthApi";
import { Link } from "react-router-dom";

export default function AddCategory() {
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const [newCategory, setNewCategory] = useState({
    name: "",
    desc: "",
    saleId: ""
  });

  const [sales, setSales] = useState([{
    name: "",
    price: 0
  }])

  const handleRoomInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    setNewCategory({ ...newCategory, [name]: value });
  };

  useEffect(() => {
    const fecthSales = async () => {
      const response = await getSales();
      try {
        setSales(response.data)
      } catch (e) {
        console.error(e);
      }
    };
    fecthSales();
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const res = await addCategory(newCategory);
    try {
      if (res.success) {
        setSuccessMessage("A new category was added successfully !");
        setNewCategory({ name: "", desc: "", saleId: "" });
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new category");
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
            <h2 className="mt-5 mb-2">Add a New Category</h2>
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
                  Category Name
                </label>
                <input
                  required
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  value={newCategory.name}
                  onChange={(e) => {
                    handleRoomInputChange(e);
                  }}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="desc" className="form-label">
                  Category Description
                </label>
                <input
                  required
                  type="text"
                  className="form-control"
                  id="desc"
                  name="desc"
                  value={newCategory.desc}
                  onChange={(e) => {
                    handleRoomInputChange(e);
                  }}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="saleId" className="form-label">
                  Sale
                </label>
                <select
                  required
                  className="form-control"
                  id="saleId"
                  name="saleId"
                  onChange={(e)=>{handleRoomInputChange(e)}}
                  defaultValue={0}
                >
                    <option value="0">Select Sale For Category</option>
                  {sales.map((sale, i) => (
                    <option key={i} value={sale.id}>
                      {sale.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="d-grid gap-2 d-md-flex mt-2">
                <Link
                  to={"/existing-categories"}
                  className="btn btn-outline-info"
                >
                  Existing categories
                </Link>
                <button type="submit" className="btn btn-outline-primary ml-5">
                  Save Category
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
}
