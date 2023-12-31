import { Cell, Pie, PieChart, ResponsiveContainer, Tooltip } from "recharts";
import { useAppSelector } from "../../../store/hooks";
import { getRandomColor } from "../../../utils/data/data";
import "./pieChartBox.scss";

const PieChartBox = () => {
  const statistics = useAppSelector(
    (state) => state.statistics.top5ProductsThisYear
  );

  return (
    <div className="pieChartBox">
      <h1>Top 5 profitable products this year</h1>
      {statistics.length > 0 ? (
        <>
          <div className="chart">
            <ResponsiveContainer width="99%" height={300}>
              <PieChart>
                <Tooltip
                  contentStyle={{ background: "white", borderRadius: "5px" }}
                />
                <Pie
                  data={statistics}
                  innerRadius={"70%"}
                  outerRadius={"90%"}
                  paddingAngle={5}
                  dataKey="value"
                >
                  {statistics.map((item) => (
                    <Cell key={item?.name} fill={getRandomColor()} />
                  ))}
                </Pie>
              </PieChart>
            </ResponsiveContainer>
          </div>
          <div className="options">
            {statistics.map((item) => (
              <div className="option" key={item?.name}>
                <div className="title">
                  <div
                    className="dot"
                    style={{ backgroundColor: getRandomColor() }}
                  />
                  <span>{item?.name}</span>
                </div>
                <span>{item?.value}</span>
              </div>
            ))}
          </div>
        </>
      ) : (
        <span className="error-text">No profitable products to show.</span>
      )}
    </div>
  );
};

export default PieChartBox;
