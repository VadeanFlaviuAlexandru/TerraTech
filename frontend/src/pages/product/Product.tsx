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
import ProductModal from "../../components/modals/productModal/ProductModal";
import {
  resetSelectedProduct,
  selectedProductSetter,
} from "../../store/SelectedProduct/SelectedProductSlice";
import { useAppDispatch, useAppSelector } from "../../store/hooks";
import { columnsProductModal } from "../../utils/data/columns";
import { warningToast } from "../../utils/toasts/userToasts";
import "./product.scss";

const Product = () => {
  const dispatch = useAppDispatch();
  const product = useAppSelector((state) => state.selectedProduct);
  const token = useAppSelector((state) => state.currentUser.token);
  const [open, setOpen] = useState(false);
  const location = useLocation();
  const id = location?.state?.id || useParams()?.id;

  const monthNameToNumber: { [key: string]: number } = {
    JANUARY: 0,
    FEBRUARY: 1,
    MARCH: 2,
    APRIL: 3,
    MAY: 4,
    JUNE: 5,
    JULY: 6,
    AUGUST: 7,
    SEPTEMBER: 8,
    OCTOBER: 9,
    NOVEMBER: 10,
    DECEMBER: 11,
  };

  const processedData = product?.chartInfo?.data?.map((dataPoint) => {
    const monthNumber = monthNameToNumber[dataPoint.name];
    const isFutureMonth = new Date().getMonth() < monthNumber;

    return {
      ...dataPoint,
      peopleNotifiedAboutProduct: isFutureMonth
        ? null
        : dataPoint.peopleNotifiedAboutProduct || 0,
      peopleSoldTo: isFutureMonth ? null : dataPoint.peopleSoldTo || 0,
    };
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        await fetchProductData(id, token).then((response) => {
          dispatch(selectedProductSetter(response));
        });
      } catch (err: any) {
        warningToast(err.stringify);
      }
    };
    fetchData();
    return () => {
      resetSelectedProduct();
    };
  }, [id]);

  function getRandomColor() {
    return "#" + Math.floor(Math.random() * 16777215).toString(16);
  }

  return (
    <div className="user">
      <div className="single">
        <div className="view">
          <div className="info">
            <div className="topInfo">
              {/* {props.img && <img src={props.img} alt="" />} */}
              <h1>{product.product.name}</h1>
              <button onClick={() => setOpen(true)}>Update</button>
            </div>
            <div className="details">
              {Object.entries(product.product).map(([key, value]) => (
                <div className="item" key={key}>
                  <span className="itemTitle">{key}</span>
                  <span className="itemValue">{value}</span>
                </div>
              ))}
            </div>
          </div>
          <hr />
          <div className="chart">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                width={500}
                height={300}
                data={processedData}
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
          headerText="Update credentials"
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
