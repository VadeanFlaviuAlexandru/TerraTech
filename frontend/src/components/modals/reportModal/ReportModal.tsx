import { GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import { updateSelectedReports } from "../../../store/SelectedUser/SelectedUserSlice";
import { useAppDispatch, useAppSelector } from "../../../store/hooks";
import "./reportModal.scss";
import { updateReport } from "../../../api/report/ReportApi";
import { updateCurrentReports } from "../../../store/CurrentUser/CurrentUserSlice";

type Props = {
  id: number;
  self: boolean;
  headerText: string;
  buttonText: string;
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
  const token = useAppSelector((state) => state.currentUser.token);
  const products = useAppSelector((state) => state.productsTable.products);

  const dispatch = useAppDispatch();

  const [report, setReport] = useState({
    id: props?.report?.id,
    productId: props?.report?.productId,
    description: props?.report?.description,
    peopleNotifiedAboutProduct: props?.report?.peopleNotifiedAboutProduct,
    peopleSoldTo: props?.report?.peopleSoldTo,
  });

  const handleChange = (field: string, value: string) => {
    setReport({ ...report, [field]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await updateReport(props.id, report, token).then((response) => {
      if (props.self) {
        dispatch(updateCurrentReports({ ...response, id: props.id }));
      } else {
        dispatch(updateSelectedReports({ ...response, id: props.id }));
      }
    });
    props.setOpen(false);
  };

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setReport({ ...report, productId: e.target.value });
  };

  return (
    <div className="add">
      <div className="modal">
        <span className="close" onClick={() => props.setOpen(false)}>
          X
        </span>
        <h1>{props.headerText}</h1>
        <form onSubmit={handleSubmit}>
          {props.columns
            .filter((item) => item.field !== "id" && item.field !== "img")
            .map((column, index) => (
              <div className="item" key={index}>
                <label htmlFor={column.field}>{column.headerName}</label>
                <input
                  id={column.field}
                  type={column.type}
                  placeholder={column.field}
                  name={column.field}
                  value={
                    (report[column.field as keyof typeof report] ??
                      "") as string
                  }
                  onChange={(e) => handleChange(column.field, e.target.value)}
                />
              </div>
            ))}
          <label>
            Select a product associated with this report:{" "}
            <select
              value={report.productId ?? report.id}
              onChange={handleSelectChange}
            >
              <option value="">Select a product</option>
              {products.map((product) => (
                <option key={product.id} value={product.id}>
                  {product.name}
                </option>
              ))}
            </select>
          </label>
          <button>{props.buttonText}</button>
        </form>
      </div>
    </div>
  );
}
