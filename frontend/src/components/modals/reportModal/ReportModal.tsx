import { GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import { updateReport } from "../../../api/report/ReportApi";
import { updateCurrentReports } from "../../../store/CurrentUser/CurrentUserSlice";
import { updateSelectedReports } from "../../../store/SelectedUser/SelectedUserSlice";
import { useAppDispatch, useAppSelector } from "../../../store/hooks";
import "./reportModal.scss";

type Props = {
  id: number;
  self: boolean;
  columns: GridColDef[];
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  report: {
    id: number;
    productId: string;
    description: String;
    peopleNotifiedAboutProduct: number;
    peopleSoldTo: number;
  };
};

export default function ReportModal(props: Props) {
  const dispatch = useAppDispatch();
  const currentUser = useAppSelector((state) => state.currentUser);
  const products = useAppSelector((state) => state.productsTable.products);
  const [report, setReport] = useState({
    id: props?.report?.id,
    productId: props?.report?.productId,
    peopleNotifiedAboutProduct: props?.report?.peopleNotifiedAboutProduct,
    peopleSoldTo: props?.report?.peopleSoldTo,
    description: props?.report?.description,
  });
  const [changedReport, setChangedReport] = useState(report);

  const handleChange = (field: string, value: string) => {
    setReport({ ...report, [field]: value });
  };

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    handleChange("productId", e?.target?.value);
  };

  const findProductName = () => {
    const matchedProduct = products.find(
      (product) => product?.id?.toString() === report?.productId
    );
    return matchedProduct && matchedProduct?.name;
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (changedReport === report) {
      props.setOpen(false);
      return;
    }
    updateReport(props?.id, report, currentUser.token).then((response) => {
      if (props.self) {
        dispatch(
          updateCurrentReports({
            ...response,
            id: props.id,
            productName: findProductName(),
          })
        );
      } else {
        dispatch(
          updateSelectedReports({
            ...response,
            id: props.id,
            productName: findProductName(),
          })
        );
      }
    });
    props.setOpen(false);
  };

  return (
    <div className="add">
      <div className="modal">
        <span className="close" onClick={() => props.setOpen(false)}>
          X
        </span>
        <h1>Update report details</h1>
        <form onSubmit={handleSubmit}>
          <div className="inputsContainer">
            <div className="dropdownContainer">
              <label>Edit the product associated with this report: </label>
              <select
                value={report?.productId ?? report?.id}
                onChange={handleSelectChange}
              >
                {products.map((product) => (
                  <option key={product?.id} value={product?.id}>
                    {product?.name}
                  </option>
                ))}
              </select>
            </div>
            {props.columns
              .filter(
                (item) => item.field !== "id" && item.field !== "description"
              )
              .map((column, index) => (
                <div className="item" key={index}>
                  <label className="itemLabel" htmlFor={column.field}>
                    {column.headerName}
                  </label>
                  <input
                    id={column.field}
                    type={column.type}
                    placeholder={column.field}
                    name={column.field}
                    maxLength={column.maxWidth}
                    onChange={(e) => handleChange(column.field, e.target.value)}
                    value={
                      (report[column.field as keyof typeof report] ??
                        "") as string
                    }
                  />
                </div>
              ))}
            <textarea
              id="description"
              name="description"
              required
              value={report.description as string}
              onChange={(e) =>
                setReport((prevReport) => ({
                  ...prevReport,
                  description: e.target.value,
                }))
              }
              maxLength={800}
            />
          </div>
          <button className="updateButton">Update report information</button>
        </form>
      </div>
    </div>
  );
}
