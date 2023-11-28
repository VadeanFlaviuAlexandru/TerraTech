import { Line, LineChart, ResponsiveContainer, Tooltip } from "recharts";
import { getRandomColor } from "../../../utils/data/data";
import "./chartBox.scss";

type Props = {
  number: number;
  percentage: number;
  chartData: {
    name: string;
    value: number;
  }[];
  dataKey: string;
  title: string;
};

const ChartBox = (props: Props) => {
  const checkForValues = (chartData: Props["chartData"]) => {
    for (const item of chartData) {
      if (item.value > 0) {
        return true;
      }
    }
    return false;
  };

  return (
    <div className="chartBox">
      <div className="boxInfo">
        <div className="title">
          <span>{props.title}</span>
        </div>
        {checkForValues(props.chartData) && (
          <h1>
            {props.title === "Total revenue this year"
              ? `$${props.number}`
              : props.number}
          </h1>
        )}
      </div>
      <div className="chartInfo">
        {checkForValues(props.chartData) ? (
          <>
            <div className="chart">
              <ResponsiveContainer width="99%" height="100%">
                <LineChart data={props.chartData}>
                  <Tooltip
                    contentStyle={{ background: "transparent", border: "none" }}
                    labelStyle={{ display: "none" }}
                    position={{ x: 0, y: -30 }}
                  />
                  <Line
                    type="monotone"
                    dataKey={props.dataKey}
                    stroke={getRandomColor()}
                    strokeWidth={2}
                    dot={false}
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
            <div className="texts">
              <span
                className="percentage"
                style={{
                  color: props?.percentage < 0 ? "tomato" : "limegreen",
                }}
              >
                {props?.percentage?.toFixed(2)?.endsWith(".00")
                  ? props?.percentage
                  : props?.percentage?.toFixed(2)}
                %
              </span>
              <span className="duration">this month</span>
            </div>
          </>
        ) : (
          <span className="error-text">Insufficient data.</span>
        )}
      </div>
    </div>
  );
};

export default ChartBox;
