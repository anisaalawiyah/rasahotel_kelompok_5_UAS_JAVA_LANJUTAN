import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  getCategory,
  getSales,
  updateCategory
} from "../utils/fecthApi.js";

export default function EditCategory() {
  const [category, setCategory] = useState({
    name: "",
    desc: "",
    saleId: "",
  });

  const [sales, setSales] = useState([
    {
      id: "",
      name: "",
      price: 0,
    },
  ]);

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { categoryId } = useParams();

  const handleInputChange = (event) => {
    let { name, value } = event.target;
    setCategory({ ...category, [name]: value });
  };

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const roomData = await getCategory(categoryId);
        setCategory(roomData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchCategory();
  },[categoryId]);

  useEffect(() => {
    const fecthSales = async () => {
      const response = await getSales();
      try {
        setSales(response.data);
      } catch (e) {
        console.error(e);
      }
    };
    fecthSales();
  },[]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await updateCategory(categoryId, category);
      if (response.success) {
        setSuccessMessage("Category updated successfully!");
        const updatedCategoryData = await getCategory(categoryId);
        setCategory(updatedCategoryData);
      } else {
        setErrorMessage("Error updating category");
      }
    } catch (error) {
      console.error(error);
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <div className="container mt-5 mb-5">
      <h3 className="text-center mb-5 mt-5">Edit Category</h3>
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
                Category Name
              </label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                value={category.name}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="desc" className="form-label hotel-color">
                Category Description
              </label>
              <input
                type="text"
                className="form-control"
                id="desc"
                name="desc"
                value={category.desc}
                onChange={handleInputChange}
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
                onChange={(e) => {
                  handleInputChange(e);
                }}
              >
                <option>Select Sale For Category</option>
                {sales.map((sale, i) => (
                    <option key={i} value={sale.id}>
                      {sale.name}
                    </option>
                  )
                )}
              </select>
            </div>

            <div className="d-grid gap-2 d-md-flex mt-2">
              <Link
                to={"/existing-categories"}
                className="btn btn-outline-info ml-5"
              >
                back
              </Link>
              <button type="submit" className="btn btn-outline-warning">
                Edit Category
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
