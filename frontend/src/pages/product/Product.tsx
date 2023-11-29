import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import {
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import { fetchProductData } from "../../api/product/ProductApi";
import EditIcon from "../../components/icons/EditIcon";
import ProductModal from "../../components/modals/productModal/ProductModal";
import {
  resetSelectedProduct,
  selectedProductSetter,
} from "../../store/SelectedProduct/SelectedProductSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsProductModal } from "../../utils/data/columns";
import {
  ProductInfoMappings,
  getRandomColor,
  processedData,
} from "../../utils/data/data";
import { ForceSignOut } from "../../utils/userUtils/userUtils";
import "./product.scss";

const Product = () => {
  const dispatch = useAppDispatch();
  const location = useLocation();
  const id = location?.state?.id || useParams()?.id;
  const product = useAppSelector((state) => state.selectedProduct);
  const currentUser = useAppSelector((state) => state.currentUser);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    fetchProductData(id, currentUser.token).then((response) => {
      dispatch(selectedProductSetter(response));
    });
    return () => {
      resetSelectedProduct();
    };
  }, [id]);

  useEffect(() => {
    if (currentUser.token === "ROLE_EMPLOYEE") {
      ForceSignOut();
    }
  }, []);

  return (
    <div className="productContainer">
      <div className="infoContainer">
        <div className="leftContainer">
          <div className="topInfo">
            <h1>{product.product.name}</h1>
            <button className="editButton" onClick={() => setOpen(true)}>
              <EditIcon />
            </button>
          </div>
          <div className="details">
            {Object.entries(product.product).map(([key, value]) => (
              <div className="item" key={key}>
                <span className="itemTitle">{ProductInfoMappings[key]}:</span>
                <span className="itemValue">{value}</span>
              </div>
            ))}
          </div>
          <div className="chart">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                width={500}
                height={300}
                data={processedData(product?.chartInfo?.data)}
                margin={{
                  top: 5,
                  right: 30,
                  left: 20,
                  bottom: 5,
                }}
              >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                {product?.chartInfo?.dataKeys?.map((dataKey) => (
                  <Line
                    type="monotone"
                    dataKey={dataKey.name}
                    stroke={getRandomColor()}
                  />
                ))}
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
      {open && (
        <ProductModal
          editableMode={true}
          headerText="Update product information"
          buttonText="Update"
          columns={columnsProductModal}
          setOpen={setOpen}
          product={product.product}
          id={product.product.id}
        />
      )}
    </div>
  );
};

export default Product;
