import { useEffect, useState } from "react";
import { fetchProductTableData } from "../../api/product/ProductApi";
import { employeeAddReport } from "../../api/user/UserApi";
import { addReport } from "../../store/CurrentUser/CurrentUserSlice";
import {
  productTableSetter,
  resetProductTable,
} from "../../store/ProductsTable/ProductTableSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import "./activity.scss";

export default function Activity() {
  const dispatch = useAppDispatch();
  const products = useAppSelector((state) => state.productsTable.products);
  const currentUser = useAppSelector((state) => state.currentUser);
  const [report, setReport] = useState({
    product_id: products[0]?.id?.toString(),
    description: "",
    peopleNotifiedAboutProduct: "",
    peopleSoldTo: "",
  });

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setReport({ ...report, product_id: e?.target?.value });
  };

  useEffect(() => {
    setReport({ ...report, product_id: products[0]?.id?.toString() });
  }, [products]);

  useEffect(() => {
    fetchProductTableData(
      currentUser.user.role === "ROLE_EMPLOYEE"
        ? currentUser.managerId
        : currentUser.user.id,
      currentUser.token
    ).then((response) => {
      dispatch(productTableSetter(response));
    });
    return () => {
      resetProductTable();
    };
  }, [currentUser.user.id]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (report.product_id == "" || products.length == 0) {
      return;
    } else {
      employeeAddReport(report, currentUser.token).then((response) => {
        dispatch(addReport(response));
      });
    }
  };

  return (
    <div className="activityPage">
      <h1>Submit a report</h1>
      <form onSubmit={handleSubmit}>
        <div className="dropdownContainer">
          <label>Select a product associated with this report:</label>
          {products.length > 0 ? (
            <select value={report?.product_id} onChange={handleSelectChange}>
              {products.map((product) => (
                <option key={product?.id} value={product?.id}>
                  {product?.name}
                </option>
              ))}
            </select>
          ) : (
            <span className="error-text">
              No products available! Cannot submit a report.
            </span>
          )}
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
            value={report?.peopleNotifiedAboutProduct}
            maxLength={9}
            onInput={(e) => {
              const inputValue = (e.target as HTMLInputElement)?.value?.replace(
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
            value={report?.peopleSoldTo}
            maxLength={9}
            onInput={(e) => {
              const inputValue = (
                e?.target as HTMLInputElement
              )?.value?.replace(/\D/g, "");
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
          maxLength={255}
          required
          value={report?.description}
          onChange={(e) =>
            setReport((prevReport) => ({
              ...prevReport,
              description: e?.target?.value,
            }))
          }
        />
        <button className="submitButton" type="submit">
          Submit report
        </button>
      </form>
    </div>
  );
}
