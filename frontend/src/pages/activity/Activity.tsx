import { useState } from "react";
import { employeeAddReport } from "../../api/user/UserApi";
import { addReport } from "../../store/CurrentUser/CurrentUserSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import "./activity.scss";

export default function Activity() {
  const dispatch = useAppDispatch();
  const products = useAppSelector((state) => state.productsTable.products);
  const token = useAppSelector((state) => state.currentUser.token);
  const [report, setReport] = useState({
    product_id: "",
    description: "",
    peopleNotifiedAboutProduct: "",
    peopleSoldTo: "",
  });

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setReport({ ...report, product_id: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (report.product_id == "") {
      return;
    } else {
      employeeAddReport(report, token).then((response) => {
        dispatch(addReport(response));
        setReport({
          product_id: "",
          description: "",
          peopleNotifiedAboutProduct: "",
          peopleSoldTo: "",
        });
      });
    }
  };

  return (
    <div className="activityPage">
      <h1>Submit a report</h1>
      <form onSubmit={handleSubmit}>
        <div className="dropdownContainer">
          <label>Select a product associated with this report:</label>
          <select value={report.product_id} onChange={handleSelectChange}>
            {products.map((product) => (
              <option key={product.id} value={product.id}>
                {product.name}
              </option>
            ))}
          </select>
        </div>
        <div className="inputContainer">
          <label htmlFor="peopleNotifiedAboutProduct">
            Specify the number of people informed about this product:
          </label>
          <input
            id="peopleNotifiedAboutProduct"
            type="text"
            pattern="\d*"
            name="peopleNotifiedAboutProduct"
            required
            value={report.peopleNotifiedAboutProduct}
            maxLength={20}
            onInput={(e) => {
              const inputValue = (e.target as HTMLInputElement).value.replace(
                /\D/g,
                ""
              );
              setReport((prevReport) => ({
                ...prevReport,
                peopleNotifiedAboutProduct: inputValue,
              }));
            }}
          />
        </div>
        <div className="inputContainer">
          <label htmlFor="peopleSoldTo">
            Input the quantity of items successfully sold:
          </label>
          <input
            id="peopleSoldTo"
            type="text"
            pattern="\d*"
            name="peopleSoldTo"
            required
            value={report.peopleSoldTo}
            maxLength={20}
            onInput={(e) => {
              const inputValue = (e.target as HTMLInputElement).value.replace(
                /\D/g,
                ""
              );
              setReport((prevReport) => ({
                ...prevReport,
                peopleSoldTo: inputValue,
              }));
            }}
          />
        </div>
        <label htmlFor="description">
          Please enter a description for the report:
        </label>
        <textarea
          id="description"
          name="description"
          required
          value={report.description}
          onChange={(e) =>
            setReport((prevReport) => ({
              ...prevReport,
              description: e.target.value,
            }))
          }
          maxLength={800}
        />
        <button className="submitButton" type="submit">
          Submit report
        </button>
      </form>
    </div>
  );
}
