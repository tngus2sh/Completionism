
import React, { useState } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./FutureExpenditurePage.css";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { useEffect } from "react";
import axios from "axios";
import { fatchFutureData } from "../redux/authSlice";
import { useDispatch } from "react-redux";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const FutureExpenditurePage = () => {
  const selectedYearAndMonth = useSelector(
    (state) => state.auth.selectedYearAndMonth
  );
  const upperNavbarName = `${selectedYearAndMonth} 미래소비`;
  const FutureExpenditureList = useSelector(
    (state) => state.auth.FutureExpenditureList
  );
  // console.log('여기는제대로 되니?',FutureExpenditureList)
  const dispatch = useDispatch();

  const [useAxios, setUseAxios] = useState(false);
  // 입력 필드와 연결된 state 변수들
  const [todo, setTodo] = useState("");
  const [cost, setCost] = useState(0);
  const [plus, setPlus] = useState(false);
  const [fixed, setFixed] = useState(false);

  //데이트 피커 전용 변수
  const [startDate, setStartDate] = useState(new Date());
  const ExampleCustomInput = ({ value, onClick }) => (
    <button className="example-custom-input" onClick={onClick}>
      {value}
    </button>
  );

  useEffect(() => {
    loadData();
  }, [useAxios]);

  const loadData = async () => {
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    try {
      const response = await axios.get("/api/schedule/future", { headers });
      console.log(response.data.dataBody);
      dispatch(fatchFutureData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };

  const createData = async () => {
    // 데이터를 객체로 만들기
    const data = {
      date: startDate.getFullYear()+'-'+(startDate.getMonth()+1).toString().padStart(2,'0')+'-' + startDate.getDate().toString().padStart(2,'0'),
      todo: todo,
      cost: cost,
      plus: plus,
      fixed: fixed,
    };

    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    try {
      const response = await axios.post("/api/schedule/future", data, {
        headers,
      });
      console.log(response);
      setUseAxios(!useAxios);
      // 요청 후 입력 필드 초기화
      setStartDate(new Date());
      setTodo("");
      setCost(0);
      setPlus(false);
      setFixed(false);
    } catch (error) {
      console.error(error);
      console.log(data)
    }
  };

  const deleteData = async (id) => {
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    try {
      const response = await axios.delete(`/api/schedule/future/${id}`, {
        headers,
      });
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar" />

      <div className="body">this is FutureExpenditurePage</div>

      <div>
        
        <DatePicker
          selected={startDate}
          onChange={(date) => setStartDate(date)}
          customInput={<ExampleCustomInput />}
          dateFormat="yyyy-MM-dd"

        />

        <div>
          <label>Todo:</label>
          <input
            type="text"
            value={todo}
            onChange={(e) => setTodo(e.target.value)}
          />
        </div>
        <div>
          <label>Cost:</label>
          <input
            type="number"
            value={cost}
            onChange={(e) => setCost(parseFloat(e.target.value))}
          />
        </div>
        <div>
          <label>Plus(체크하면 수입):</label>
          <input
            type="checkbox"
            checked={plus}
            onChange={(e) => setPlus(e.target.checked)}
          />
        </div>
        {/* <div>
          <label>Fixed:</label>
          <input
            type="checkbox"
            checked={fixed}
            onChange={(e) => setFixed(e.target.checked)}
          />
        </div> */}
        <button onClick={createData}>미래소비 생성하기</button>
        <hr />
      </div>

      <div>
        {FutureExpenditureList.map((item, index) => {

            if(item.date.slice(0,7)===selectedYearAndMonth.slice(0,7)){
                return (
                    <div>
                      id: {item.id}|{item.date}|{item.todo}|{item.cost}|{item.plus}
                      <button
                        onClick={() => {
                          deleteData(item.id);
                          setUseAxios(!useAxios);
                        }}
                      >
                        삭제
                      </button>
                      <hr />
                    </div>
                  );
            }
          
        })}
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default FutureExpenditurePage;
