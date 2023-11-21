import { useState } from "react";
import { useAppSelector } from "../../store/hooks";
import { employeeAddReport } from "../../api/user/UserApi";

const Activity = () => {
  const products = useAppSelector((state) => state.productsTable.products);
  const token = useAppSelector((state) => state.currentUser.token);
  const [report, setReport] = useState({
    product_id: "",
    description: "",
    peopleNotifiedAboutProduct: "",
    peopleSoldTo: "",
  });

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setReport({ ...report, product_id: e.target.value }); // Update the product_id in the report state
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (report.product_id == "") {
      return;
    } else {
      try {
        const res = await employeeAddReport(report, token);
      } catch (err) {}
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Select a product associated with this report:{" "}
        <select value={report.product_id} onChange={handleSelectChange}>
          <option value="">Select a product</option>
          {products.map((product) => (
            <option key={product.id} value={product.id}>
              {product.name}
            </option>
          ))}
        </select>
      </label>
      <label htmlFor="description">Description</label>
      <input
        id="description"
        type="text"
        name="description"
        value={report.description}
        onChange={(e) => setReport({ ...report, description: e.target.value })}
      />
      <label htmlFor="peopleNotifiedAboutProduct">
        People Notified About Product
      </label>
      <input
        id="peopleNotifiedAboutProduct"
        type="text"
        name="peopleNotifiedAboutProduct"
        value={report.peopleNotifiedAboutProduct}
        onChange={(e) =>
          setReport({ ...report, peopleNotifiedAboutProduct: e.target.value })
        }
      />
      <label htmlFor="peopleSoldTo">People Sold To</label>
      <input
        id="peopleSoldTo"
        type="text"
        name="peopleSoldTo"
        value={report.peopleSoldTo}
        onChange={(e) => setReport({ ...report, peopleSoldTo: e.target.value })}
      />
      <button type="submit">Add report</button>
    </form>
  );
};

export default Activity;
