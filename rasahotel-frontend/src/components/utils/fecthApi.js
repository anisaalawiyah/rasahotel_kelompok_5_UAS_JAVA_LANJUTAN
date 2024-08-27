import axios from "axios";

const token = localStorage.getItem("token");

export const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export const getHeader = () => {
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

// Costumer & User

/* fungsi ini untuk mendaftarkan pelanggan dan pengguna baru */
export async function registerUser(registration) {
  try {
    const response = await api.post("/customer/register", registration);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Customer registration error : ${error.message}`);
    }
  }
}

// Fungsi ini untuk resetPassword
export async function resetPassword(data) {
  try {
    const response = await api.post("/auth/reset-password", data);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`User reset password error : ${error.message}`);
    }
  }
}

// Fungsi ini untuk meminta untuk resetPassword
export async function forgotPassword(email) {
  try {
    const response = await api.post("/auth/forgot-password?email=" + email);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`User forgot password error : ${error.message}`);
    }
  }
}

/* fungsi ini untuk login */
export async function login(login) {
  try {
    const response = await api.post("/auth/login", login);
    return response.data.data;
  } catch (error) {
    console.error(error);
    return null;
  }
}

/*  fungsi ini untuk mendapatkan informasi pengguna */
export async function getUserProfile(userId) {
  const response = await api.get("/customer/get-user?email=" + userId, {
    headers: getHeader(),
  });
  return response.data;
}

/* fungsi ini untuk menghapus pengguna */
export async function deleteUser(userId) {
  try {
    const response = await api.delete(`/auth/delete-user?email=${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    return error.message;
  }
}

/* fungsi ini untuk mendapatkan data semua pengguna */
export async function getUsers() {
  const response = await api.get(`/customer/get-users`, {
    headers: getHeader(),
  });
  return response.data;
}

// Room

// fungsi ini untuk menambahkan data kamar baru
export async function addRoom(data) {
  const response = await api.post("/room/add-room", data, {
    headers: getHeader(),
  });

  return response.data;
}

// fungsi ini untuk mendapatkan data seluruh kamar
export async function getRooms() {
  try {
    const response = await api.get("/room/all-room");
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

// fungsi ini untuk mengubah data kamar berdasarkan id
export const updateRoom = async (idRoom, data) => {
  const response = await api
    .put("/room/update-room/" + idRoom, data, {
      headers: getHeader(),
    })
    .then((res) => {
      console.log(res);
      return res.data;
    })
    .catch((e) => console.error(e));
  return response;
};

// fungsi ini untuk mengubah data kamar berdasarkan id
export async function updatePhotoRoom(idRoom, data) {
  try {
    const response = await api.putForm(
      "/room/update-room-photo?idRoom=" + idRoom,
      data,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "Multipart/form-data",
        },
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

// fungsi ini untuk mendapatkan data kamar berdasarkan id
export async function getRoom(idRoom) {
  try {
    const response = await api.get("/room/get-room?idRoom=" + idRoom);
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

// Fungsi ini untuk menghapus data kamar berdasarkan id
export async function deleteRoom(roomId) {
  try {
    const response = await api.delete("/room/delete-room?idRoom=" + roomId, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

// Sales

// fungsi ini untuk menambahkan data promo baru
export async function addSale(data) {
  try {
    const response = await api.post("/category/add-sale", data, {
      headers: getHeader(),
    });
    return response;
  } catch (e) {
    console.error(e);
  }
}

// fungsi ini untuk mendapatkan data seluruh promo
export async function getSales() {
  try {
    const response = await api.get("/category/all-sale");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching sale");
  }
}

// fungsi ini untuk mengubah data promo berdasarkan id
export async function updateSale(idSale, data) {
  try {
    const response = await api.put(
      "/category/update-sale?idSale=" + idSale,
      data,
      {
        headers: getHeader(),
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching sale types");
  }
}

// fungsi ini untuk mendapatkan data promo berdasarkan id
export async function getSale(idSale) {
  try {
    const response = await api.get("/category/get-sale?idSale=" + idSale);
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching sale types");
  }
}

// Fungsi ini untuk menghapus data promo berdasarkan id
export async function deleteSale(idSale) {
  try {
    const response = await api.delete(
      "/category/delete-sale?idSale=" + idSale,
      {
        headers: getHeader(),
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching sale types");
  }
}

// Category

// fungsi ini untuk menambahkan data kategory baru
export async function addCategory(data) {
  try {
    const response = await api.post("/category/add-category", data, {
      headers: getHeader(),
    });
    return response.data;
  } catch (e) {
    console.error(e);
  }
}

// fungsi ini untuk mendapatkan data seluruh kategory
export async function getCategories() {
  try {
    const response = await api.get("/category/all-category");
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching category");
  }
}

// fungsi ini untuk mengubah data kategory berdasarkan id
export async function updateCategory(idCategory, data) {
  try {
    const response = await api.put(
      "/category/update-category?idCategory=" + idCategory,
      data,
      {
        headers: getHeader(),
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching category types");
  }
}

// fungsi ini untuk mendapatkan data kategory berdasarkan id
export async function getCategory(idCategory) {
  try {
    const response = await api.get(
      "/category/get-category?idCategory=" + idCategory
    );
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching category types");
  }
}

// Fungsi ini untuk menghapus data kategory berdasarkan id
export async function deleteCategory(idCategory) {
  try {
    const response = await api.delete(
      "/category/delete-category?idCategory=" + idCategory,
      {
        headers: getHeader(),
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching category types");
  }
}

// Booking

export async function getBookingsByUserId(userId) {
  try {
    const response = await api.get("/booking/get-by-email?email=" + userId, {
      headers: getHeader(),
    });
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching bookings by userId");
  }
}

export async function getAllBookings() {
  try {
    const response = await api.get("/booking/all-bookings", {
      headers: getHeader(),
    });
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching bookings by userId");
  }
}

export async function findBookingByEmail(email) {
  try {
    const response = await api.get("/booking/find-booking/" + email, {
      headers: getHeader(),
    });
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching bookings by userId");
  }
}

export async function cancelBooking(bookingId) {
  try {
    const response = await api.delete("/booking/cancel-booking/" + bookingId, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw new Error("Error fetching bookings by userId");
  }
}

export async function saveBooking(roomId, data) {
  const response = await api.post("/booking/save-booking/" + roomId, data, {
    headers: getHeader(),
  });
  return response.data.data;
}

// PaymentGateway

export async function checkout(confirmationCode) {
  try {
    const response = await api.post(
      "/booking/payment?confirmationCode=" + confirmationCode,
      {
        headers: getHeader(),
      }
    );
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching transaction type");
  }
}

export async function checkoutSuccess(bookingId) {
  try {
    const response = await api.post(
      "/booking/paymentSuccess?bookingId=" + bookingId,
      {
        headers: getHeader(),
      }
    );
    return response.data.data;
  } catch (error) {
    throw new Error("Error fetching transaction type");
  }
}

// Report Data Booking
export async function getReportPdf() {
  try {
    const response = await api.get("/report/report-pdf", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Disposition": "Attachment; filename=report-booking.pdf",
      },
      responseType: "blob",
    });
    const blob = new Blob([response.data], {
      type: "application/pdf",
    });
    const pdfFile = URL.createObjectURL(blob);
    location.href = pdfFile;
  } catch (error) {
    throw new Error("Error fetching report pdf");
  }
}

export async function getReportAnnual() {
  try {
    const response = await api.get("/report/report-annual", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Disposition": "Attachment; filename=report-booking.xlsx",
      },
      responseType: "blob",
    });
    const blob = new Blob([response.data], {
      type: "application/excel",
    });
    const pdfFile = URL.createObjectURL(blob);
    location.href = pdfFile;
  } catch (error) {
    throw new Error("Error fetching annual");
  }
}

export async function getReportMonthly() {
  try {
    const response = await api.get("/report/report-monthly", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Disposition": "Attachment; filename=report-booking.xlsx",
      },
      responseType: "blob",
    });
    const blob = new Blob([response.data], {
      type: "application/excel",
    });
    const pdfFile = URL.createObjectURL(blob);
    location.href = pdfFile;
  } catch (error) {
    throw new Error("Error fetching report month");
  }
}

export async function getReportDaily() {
  try {
    const response = await api.get("/report/report-daily", {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Disposition": "Attachment; filename=report-booking.xlsx",
      },
      responseType: "blob",
    });
    const blob = new Blob([response.data], {
      type: "application/excel",
    });
    const pdfFile = URL.createObjectURL(blob);
    location.href = pdfFile;
  } catch (error) {
    throw new Error("Error fetching report daily");
  }
}

