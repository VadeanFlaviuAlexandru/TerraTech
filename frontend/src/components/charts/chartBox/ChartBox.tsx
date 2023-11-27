import { Line, LineChart, ResponsiveContainer, Tooltip } from "recharts";
import "./chartBox.scss";
import { getRandomColor } from "../../../utils/data/data";

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
  return (
    <div className="chartBox">
      <div className="boxInfo">
        <div className="title">
          <span>{props.title}</span>
        </div>
        <h1>
          {props.title === "Total revenue this year"
            ? `$${props.number}`
            : props.number}
        </h1>
      </div>
      <div className="chartInfo">
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
      </div>
    </div>
  );
};

export default ChartBox;
