/* eslint-disable react/prop-types */
import { useState } from "react";

export default function RoomFilter({ data, setFilteredData }) {
  const [filter, setFilter] = useState("");

  const handleSelectChange = (e) => {
    const selectedType = e.target.value;
    if (selectedType === "") clearFilter();
    else {
      setFilter(selectedType);

      const filteredRooms = data.filter(
        (room) => room.category.name === selectedType
      );
      setFilteredData(filteredRooms);
    }
  };

  const clearFilter = () => {
    setFilter("");
    setFilteredData(data);
  };

  const category = [...new Set(data.map((room) => room.category.name))];

  return (
    <div className="input-group mb-3">
      <span className="input-group-text" id="room-type-filter">
        Filter Rooms By Category
      </span>
      <select
        className="form-select"
        aria-label="romm type filter"
        value={filter}
        onChange={handleSelectChange}
      >
        <option value="">Select a room category to filter....</option>
        {category.map((type, index) => (
          <option key={index} value={String(type)}>
            {String(type)}
          </option>
        ))}
      </select>
      <button className="btn btn-hotel" type="button" onClick={clearFilter}>
        Clear Filter
      </button>
    </div>
  );
}
