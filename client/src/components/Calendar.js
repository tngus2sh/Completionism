import React, { useEffect, useState } from "react";
import { Icon } from "@iconify/react";
import { format, addMonths, subMonths } from "date-fns";
import { startOfMonth, endOfMonth, startOfWeek, endOfWeek } from "date-fns";
import { isSameMonth, isSameDay, addDays } from "date-fns";
import "./Calendar.css";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setSelectedYearAndMonth } from "../redux/authSlice";
import { useSelector } from "react-redux";

const RenderHeader = ({ currentMonth, prevMonth, nextMonth }) => {
  return (
    <div className="header row">
      <div className="col col-start">
        <span className="text">
          <span className="text month">{format(currentMonth, "M")}월</span>
          {format(currentMonth, "yyyy")}
        </span>
      </div>
      <div className="col col-end">
        <Icon icon="bi:arrow-left-circle-fill" onClick={prevMonth} />
        <Icon icon="bi:arrow-right-circle-fill" onClick={nextMonth} />
      </div>
    </div>
  );
};

const RenderDays = () => {
  const days = [];
  const date = ["Sun", "Mon", "Thu", "Wed", "Thrs", "Fri", "Sat"];

  for (let i = 0; i < 7; i++) {
    days.push(
      <div className="col" key={i}>
        {date[i]}
      </div>
    );
  }

  return <div className="days row">{days}</div>;
};

const RenderCells = ({ currentMonth, selectedDate, onDateClick }) => {
  const monthStart = startOfMonth(currentMonth);
  const monthEnd = endOfMonth(monthStart);
  const startDate = startOfWeek(monthStart);
  const endDate = endOfWeek(monthEnd);

  const rows = [];
  let days = [];
  let day = startDate;
  let formattedDate = "";

  while (day <= endDate) {
    for (let i = 0; i < 7; i++) {
      formattedDate = format(day, "d");
      const cloneDay = day;

      // const cellData = data.day.find(item => item.day.split('-').pop() === format(cloneDay, 'dd'));

      days.push(
        <div
          className={`col cell ${
            !isSameMonth(day, monthStart)
              ? "disabled"
              : isSameDay(day, selectedDate)
              ? "selected"
              : format(currentMonth, "M") !== format(day, "M")
              ? "not-valid"
              : "valid"
          }`}
          key={day}
          onClick={() => onDateClick(cloneDay)}
        >
          <span
            className={
              format(currentMonth, "M") !== format(day, "M")
                ? "text not-valid"
                : ""
            }
          >
            {formattedDate}
          </span>
          {/* {cellData && (
                        <div className="income-spend">
                            <span className="income">Income: {cellData.income}</span>
                            <span className="spend">Spend: {cellData.spend}</span>
                        </div>
                    )} */}
        </div>
      );
      day = addDays(day, 1);
    }
    rows.push(
      <div className="row" key={day}>
        {days}
      </div>
    );
    days = [];
  }
  return <div className="body">{rows}</div>;
};

export const Calendar = () => {
  // useEffect(()=>{
  //     loadData();
  // },[])
  // console.log('props',props)

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [currentMonth, setCurrentMonth] = useState(new Date());
  const [selectedDate] = useState(new Date());

  const prevMonth = () => {
    const previousMonth = subMonths(currentMonth, 1);
    const payload = `${previousMonth.getFullYear()}-${(
      previousMonth.getMonth() + 1
    )
      .toString()
      .padStart(2, "0")}`;
    setCurrentMonth(previousMonth);
    dispatch(setSelectedYearAndMonth(payload));
  };

  const nextMonth = () => {
    const nextMonthDate = addMonths(currentMonth, 1);
    const payload = `${nextMonthDate.getFullYear()}-${(
      nextMonthDate.getMonth() + 1
    )
      .toString()
      .padStart(2, "0")}`;
    setCurrentMonth(nextMonthDate);
    dispatch(setSelectedYearAndMonth(payload));
  };
  const onDateClick = (input) => {
    const dateObj = new Date(input);
    const year = dateObj.getFullYear();
    const month = (dateObj.getMonth() + 1).toString().padStart(2, "0");
    const day = dateObj.getDate().toString().padStart(2, "0");
    const id = `${year}${month}${day}`;
    //   navigate(`../diary/${id}`);
    navigate(`./${id}`);
    
  };
  return (
    <div className="calendar">
      가계부달력
      <RenderHeader
        currentMonth={currentMonth}
        prevMonth={prevMonth}
        nextMonth={nextMonth}
      />
      <RenderDays />
      <RenderCells
        currentMonth={currentMonth}
        selectedDate={selectedDate}
        onDateClick={onDateClick}
      />
    </div>
  );
};
