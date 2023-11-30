import { GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import {
  addProductInTable,
  updateProductData,
} from "../../../api/product/ProductApi";
import { addProduct } from "../../../store/ProductsTable/ProductTableSlice";
import { updateSelectedProduct } from "../../../store/SelectedProduct/SelectedProductSlice";
import { useAppDispatch, useAppSelector } from "../../../store/hooks";
import "./productModal.scss";

type Props = {
  editableMode: boolean;
  id: number;
  headerText: string;
  buttonText: string;
  columns: GridColDef[];
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
  product: {
    name: String | null;
    price: Number | null;
    producer: String | null;
    inStock: Number | null;
  } | null;
};

export default function ProductModal(props: Props) {
  const token = useAppSelector((state) => state.currentUser.token);
  const dispatch = useAppDispatch();

  const [product, setProduct] = useState({
    name: props?.product?.name,
    price: props?.product?.price,
    producer: props?.product?.producer,
    inStock: props?.product?.inStock,
  });

  const handleChange = (field: string, value: string) => {
    setProduct({ ...product, [field]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (props.editableMode) {
      await updateProductData(props.id, product, token).then((response) => {
        dispatch(updateSelectedProduct(response));
      });
    } else {
      await addProductInTable(product, token).then((response) => {
        dispatch(addProduct(response));
      });
    }

    props.setOpen(false);
  };

  return (
    <div className="add">
      <div className="modal">
        <span className="close" onClick={() => props.setOpen(false)}>
          X
        </span>
        <h1>{props.headerText}</h1>
        <form onSubmit={handleSubmit}>
          <div className="inputsContainer">
            {props.columns.map((column, index) => (
              <div className="item" key={index}>
                <label className="itemLabel" htmlFor={column.field}>
                  {column.headerName}
                </label>
                <input
                  id={column.field}
                  type={column.type}
                  name={column.field}
                  required={!props.editableMode}
                  maxLength={column.maxWidth}
                  value={
                    (product[column.field as keyof typeof product] ??
                      "") as string
                  }
                  onChange={(e) =>
                    column.field === "price" || column.field === "inStock"
                      ? (() => {
                          const inputValue = (
                            e?.target as HTMLInputElement
                          )?.value?.replace(/\D/g, "");
                          handleChange(column.field, inputValue);
                        })()
                      : handleChange(column.field, e.target.value)
                  }
                />
              </div>
            ))}
            <button className="updateButton">{props.buttonText}</button>
          </div>
        </form>
      </div>
    </div>
  );
}
