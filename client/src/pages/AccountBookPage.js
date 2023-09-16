import React, { useEffect } from "react";
import Modal from "react-modal";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Calendar } from "../components/Calendar";
import { CalenderForDiary } from "../components/CalendarForDiary";
import SwipeableTemporaryDrawer from "../components/bottomDrawer";
import { Link } from "react-router-dom";
import { setIsDiary } from "../redux/authSlice";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { useState } from "react";
import axios from "axios";
import { fatchMonthTransactionData, fatchTotalBudgetData, fatchMonthTransactionData500 } from "../redux/authSlice";
import DoneRoundedIcon from "@mui/icons-material/DoneRounded";
import { setSelectedYearAndMonth } from "../redux/authSlice";

// 모달 스타일을 설정합니다.
const modalStyle = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    border: "1px solid #919191",
  },
  overlay: {
    position: "fixed",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: "rgba(0, 0, 0, 0.3)",
    backdropFilter: "blur(2px)",
  },
};

const AccountBookPage = () => {
  const upperNavbarName = "가계부";
  const isDiary = useSelector((state) => state.auth.isDiary);
  const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);

  const MonthTransactionData = useSelector((state) => state.auth.MonthTransactionData);
  const totalBudgetData = useSelector((state) => state.auth.totalBudgetData);
  const [useAxios, setUseAxios] = useState(false);

  const todayDate = new Date();
  const temp = todayDate.getFullYear().toString() + "-" + (todayDate.getMonth() + 1).toString().padStart(2, "0");
  const dispatch = useDispatch();
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  const [budgetData, setBudgetData] = useState({
    yearMonth: null,
    totalBudget: null,
    category: "TOTAL", // 기본 카테고리 설정
  });

  useEffect(() => {
    const fetchData = async () => {
      await dispatch(setSelectedYearAndMonth(temp));
      await loadData();
      await loadBudgetData();
    };

    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      await dispatch(setSelectedYearAndMonth(temp));
      await loadData();
      await loadBudgetData();
    };
    fetchData();
  }, [useAxios, selectedYearAndMonth]);

  const loadData = async () => {
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    let firstDayOfMonth = null;
    let lastDayOfMonth = null;
    if (selectedYearAndMonth) {
      const year = selectedYearAndMonth.split("-")[0];  //selectedYearAndMonth = YYYY-MM 형식
      const month = selectedYearAndMonth.split("-")[1];
      firstDayOfMonth = `${year}-${month}-01`;
      lastDayOfMonth = `${year}-${month}-${new Date(year, month, 0).getDate()}`;
    }

    try {
      const response = await axios.get(`/api/history/${firstDayOfMonth}_${lastDayOfMonth}`, { headers });
      // console.log(response.data);
      dispatch(fatchMonthTransactionData(response.data.dataBody));
    } catch (error) {
      console.error(error);
      dispatch(fatchMonthTransactionData500());
    }
  };

  // 예산 전체조회
  const loadBudgetData = async (e) => {
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");
    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };
    try {
      //요청 보내기
      const response = await axios.get(`/api/budget`, { headers });
      // console.log(response.data);
      dispatch(fatchTotalBudgetData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };

  // 예산 생성 모달 열기
  const openCreateModal = () => {
    setBudgetData({
      yearMonth: selectedYearAndMonth + "-" + "01",
      totalBudget: null,
      category: "TOTAL", // 기본 카테고리 설정
    });
    setIsCreateModalOpen(true);
  };

  // 예산 수정 모달 열기
  const openEditModal = (selectedCategory) => {
    setBudgetData({
      yearMonth: selectedYearAndMonth + "-" + "01",
      totalBudget: totalBudgetData.find((item) => item.category === selectedCategory)?.totalBudget,
      category: selectedCategory,
    });
    setIsEditModalOpen(true);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setBudgetData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleCategoryChange = (e) => {
    const selectedCategory = e.target.value;
    setBudgetData((prevData) => ({
      ...prevData,
      category: selectedCategory,
    }));
  };

  const createBudget = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const data = {
      yearMonth: budgetData.yearMonth,
      totalBudget: budgetData.totalBudget,
      category: budgetData.category,
    };
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
    try {
      const response = await axios.post(`/api/budget`, data, {
        headers,
      });
      console.log(response.data);
      setUseAxios(!useAxios);
      setIsCreateModalOpen(false);
    } catch (error) {
      console.error(error);
    }
  };

  const updateBudget = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const data = {
      yearMonth: budgetData.yearMonth,
      totalBudget: budgetData.totalBudget,
      category: budgetData.category,
    };
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
    try {
      const response = await axios.patch(`/api/budget`, data, {
        headers,
      });
      console.log(response.data);
      setUseAxios(!useAxios);
      setIsEditModalOpen(false);
    } catch (error) {
      console.error(error);
    }
  };

  function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  setScreenSize();
  window.addEventListener("resize", setScreenSize);

  return (
    <div className="accountbook-page">
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="accountbook-info-container">
        <div style={{ marginTop: "1.7rem" }}>
          <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
          <span>현재 잔액 : 원</span>
        </div>
        <div style={{ marginBottom: "1.7rem" }}>
          <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
          <span>이번 달 나갈 예정인 금액 : 원</span>
        </div>
      </div>

      <div>
        <button onClick={openCreateModal}>예산(budget) 작성하기</button>
      </div>
      <Modal isOpen={isCreateModalOpen} onRequestClose={() => setIsCreateModalOpen(false)} style={modalStyle} contentLabel="예산 작성 모달">
        <h2>{selectedYearAndMonth} 예산 생성</h2>
        <div>
          <label htmlFor="totalBudget">총 예산</label>
          <input type="text" id="totalBudget" name="totalBudget" value={budgetData.totalBudget || ""} onChange={handleInputChange} />
        </div>
        <div>
          <label htmlFor="category">카테고리</label>
          <select id="category" name="category" value={budgetData.category || "TOTAL"} onChange={handleCategoryChange}>
            <option value="TOTAL">전체</option>
            <option value="TRAFFIC">교통</option>
            <option value="FOOD">식비</option>
            <option value="SHOPPING">쇼핑</option>
            <option value="LIFE">생활</option>
            <option value="ETC">기타</option>
          </select>
        </div>
        <button onClick={createBudget}>예산 생성하기</button>
      </Modal>

      <Modal isOpen={isEditModalOpen} onRequestClose={() => setIsEditModalOpen(false)} style={modalStyle} contentLabel="예산 수정 모달">
        <h2>{selectedYearAndMonth} 예산 수정</h2>
        <div>
          <label htmlFor="totalBudget">총 예산</label>
          <input type="text" id="totalBudget" name="totalBudget" value={budgetData.totalBudget || ""} onChange={handleInputChange} />
        </div>
        <div>
          <label htmlFor="category">카테고리</label>
          <select id="category" name="category" value={budgetData.category || "TOTAL"} onChange={handleCategoryChange}>
            <option value="TOTAL">전체</option>
            <option value="TRAFFIC">교통</option>
            <option value="FOOD">식비</option>
            <option value="SHOPPING">쇼핑</option>
            <option value="LIFE">생활</option>
            <option value="ETC">기타</option>
          </select>
        </div>
        <button onClick={updateBudget}>예산 수정하기</button>
      </Modal>

      <div className="calendar-container">
        <Calendar />
        {/* {isDiary ? <CalenderForDiary /> : <Calendar />} */}
      </div>

      <div>
        {selectedYearAndMonth} 예산
        {totalBudgetData.map((item, index) => {
          if (item.yearMonth.slice(0, 7) === selectedYearAndMonth.slice(0, 7)) {
            return (
              <div key={item.id}>
                {item.id}|{item.yearMonth}|{item.memberId}|{item.totalBudget}|{item.category}|<button onClick={() => openEditModal(item.category)}>수정</button>
              </div>
            );
          }
        })}
      </div>

      <div className="accountbook-button-container">
        <div className="accountbook-button">
          <Link to="/future" className="accountbook-link">
            미래 예상소비 등록
          </Link>
        </div>
        <div className="accountbook-button">
          <Link to="/fixed" className="accountbook-link">
            고정 지출 등록
          </Link>
        </div>
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default AccountBookPage;
