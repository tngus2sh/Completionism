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
    position: "fixed",
    top: "0",
    left: "0",
    rigiht: "0",
    width: "100%",
    height: "25rem",
    padding: "0",
    borderRadius: "0 0 1rem 1rem",
    textAlign: "center",
    overflowY: "auto", // 스크롤바 추가
    outline: "none",
    borderBottom: "1px solid #919191",
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

  const [nowExpenseMoney, setNowExpenseMoney] = useState(0);
  const [budgetData, setBudgetData] = useState({
    yearMonth: null,
    totalBudget: null,
    category: "TOTAL", // 기본 카테고리 설정
  });

  const [nextFutureScheduleMoney, setNextFutureScheduleMoney] = useState(0);
  const [nextPinnedScheduleMoney, setNextPinnedScheduleMoney] = useState(0);

  useEffect(() => {
    console.log("future: ", nextFutureScheduleMoney, ", pinned: ", nextPinnedScheduleMoney);
  }, [nextFutureScheduleMoney, nextPinnedScheduleMoney]);

  useEffect(() => {
    const fetchData = async () => {
      await dispatch(setSelectedYearAndMonth(temp));
      await loadData();
      await loadBudgetData();
      await loadFutureMoney();
      await loadPinnedMoney();
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
      const year = selectedYearAndMonth.split("-")[0]; //selectedYearAndMonth = YYYY-MM 형식
      const month = selectedYearAndMonth.split("-")[1];
      firstDayOfMonth = `${year}-${month}-01`;
      lastDayOfMonth = `${year}-${month}-${new Date(year, month, 0).getDate()}`;
    }

    try {
      console.log(firstDayOfMonth);
      console.log(lastDayOfMonth);
      const response = await axios.get(`/api/history/${firstDayOfMonth}_${lastDayOfMonth}`, { headers });
      // console.log(response.data);
      dispatch(fatchMonthTransactionData(response.data.dataBody));
    } catch (error) {
      console.error(error);
      dispatch(fatchMonthTransactionData500());
    }
  };

  // 미래 예상 소비 조회 (내일 ~ 말일)
  const loadFutureMoney = async (e) => {
    const now = new Date();

    const year = now.getFullYear();
    const month = ("0" + (now.getMonth() + 1)).slice(-2);
    const day = ("0" + now.getDate()).slice(-2);

    const today = year + "-" + month + "-" + day;

    const accessToken = localStorage.getItem("accessToken");
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };
    try {
      const response = await axios.get(`/api/schedule/future/next/${today}`, { headers });
      setNextFutureScheduleMoney(response.data.dataBody);
    } catch (error) {
      console.error(error);
    }
  };

  // 고정 지출 소비 조회 (내일 ~ 말일)
  const loadPinnedMoney = async (e) => {
    const now = new Date();

    const year = now.getFullYear();
    const month = ("0" + (now.getMonth() + 1)).slice(-2);
    const day = ("0" + now.getDate()).slice(-2);

    const today = year + "-" + month + "-" + day;

    const accessToken = localStorage.getItem("accessToken");
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };
    try {
      const response = await axios.get(`/api/schedule/pinned/next/${today}`, { headers });
      setNextPinnedScheduleMoney(response.data.dataBody);
    } catch (error) {
      console.error(error);
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

  function totalBudgetDataIsPresent() {
    if (totalBudgetData != null && totalBudgetData.length > 0) return true;
    return false;
  }

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
        {totalBudgetDataIsPresent() && (
          <div>
            <div className="budget-update-button-container">
              <button className="budget-modal-button" onClick={() => openEditModal("TOTAL")}>
                예산 수정
              </button>
            </div>
            <div style={{ marginTop: "0.7rem" }}>
              <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
              <span>이번달 예산 : {totalBudgetData[0].totalBudget}원</span>
            </div>
            <div>
              <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
              <span>현재까지 사용 금액 : {MonthTransactionData.spend}원</span>
            </div>
            <div style={{ marginBottom: "1.7rem" }}>
              <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
              <span>남은 자유 금액 : {totalBudgetData[0].totalBudget + nextFutureScheduleMoney + nextPinnedScheduleMoney}원</span>
            </div>
          </div>
        )}
        {!totalBudgetDataIsPresent() && (
          <div>
            <div className="budget-create-container">
              <div style={{ marginTop: "1.7rem", fontSize: "0.9rem" }}>🔔아직 등록된 예산이 없어요. 등록하실래요?</div>
              <button className="budget-modal-button" onClick={openCreateModal} style={{ marginTop: "0.7rem", marginBottom: "1.7rem" }}>
                <b>이번달 예산 등록하기</b>
              </button>
            </div>

            {/* <div className="budget-update-button-container">
              <button className="budget-update-button" onClick={() => openEditModal("TOTAL")}>
                예산 수정
              </button>
            </div> */}
          </div>
        )}
      </div>

      {/* <div>
        <button onClick={openCreateModal}>예산(budget) 작성하기</button>
      </div> */}

      <Modal isOpen={isCreateModalOpen} onRequestClose={() => setIsCreateModalOpen(false)} style={modalStyle} contentLabel="예산 작성 모달">
        <div style={{ display: "inline-block", width: "90%", height: "100%" }}>
          <div className="fixed-header-container" style={{ marginTop: "2.5rem" }}>
            <h3>예산 생성</h3>
          </div>

          <div className="budget-container">
            <div className="budget-create-flex-container" style={{ marginTop: "3rem" }}>
              <div className="budget-create-info-container">총 예산</div>
              <div className="budget-create-content-container">
                <div className="budget-create-border-container">
                  <div className="budget-create-border-flex-container" style={{ width: "100%", height: "100%", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <input type="number" id="totalBudget" name="totalBudget" value={budgetData.totalBudget || ""} onChange={handleInputChange} />
                  </div>
                </div>
              </div>
            </div>

            <div className="budget-create-flex-container" style={{ marginBottom: "2rem" }}>
              <div className="budget-create-info-container">카테고리</div>
              <div className="budget-create-content-container">
                <div className="budget-create-border-container">
                  <div className="budget-create-border-flex-container" style={{ height: "100%", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <select className="budget-category" id="category" name="category" value={budgetData.category || "TOTAL"} onChange={handleCategoryChange}>
                      <option value="TOTAL">전체</option>
                      <option value="TRAFFIC">교통</option>
                      <option value="FOOD">식비</option>
                      <option value="SHOPPING">쇼핑</option>
                      <option value="LIFE">생활</option>
                      <option value="ETC">기타</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>

            <div className="budget-create-flex-container">
              <button onClick={createBudget} className="budget-button">
                예산 생성하기
              </button>
            </div>
          </div>
        </div>
      </Modal>

      <Modal isOpen={isEditModalOpen} onRequestClose={() => setIsEditModalOpen(false)} style={modalStyle} contentLabel="예산 수정 모달">
        <div style={{ display: "inline-block", width: "90%", height: "100%" }}>
          <div className="fixed-header-container" style={{ marginTop: "2.5rem" }}>
            <h3>예산 수정</h3>
          </div>

          <div className="budget-container">
            <div className="budget-create-flex-container" style={{ marginTop: "3rem" }}>
              <div className="budget-create-info-container">총 예산</div>
              <div className="budget-create-content-container">
                <div className="budget-create-border-container">
                  <div className="budget-create-border-flex-container" style={{ width: "100%", height: "100%", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <input type="text" id="totalBudget" name="totalBudget" value={budgetData.totalBudget || ""} onChange={handleInputChange} />
                  </div>
                </div>
              </div>
            </div>

            <div className="budget-create-flex-container" style={{ marginBottom: "2rem" }}>
              <div className="budget-create-info-container">카테고리</div>
              <div className="budget-create-content-container">
                <div className="budget-create-border-container">
                  <div className="budget-create-border-flex-container" style={{ height: "100%", display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <select className="budget-category" id="category" name="category" value={budgetData.category || "TOTAL"} onChange={handleCategoryChange}>
                      <option value="TOTAL">전체</option>
                      <option value="TRAFFIC">교통</option>
                      <option value="FOOD">식비</option>
                      <option value="SHOPPING">쇼핑</option>
                      <option value="LIFE">생활</option>
                      <option value="ETC">기타</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>

            <div className="budget-create-flex-container">
              <button onClick={updateBudget} className="budget-button">
                예산 수정하기
              </button>
            </div>
          </div>
        </div>
      </Modal>

      <div className="calendar-container">
        <Calendar />
        {/* {isDiary ? <CalenderForDiary /> : <Calendar />} */}
      </div>

      {/* <div>
        {totalBudgetData.map((item, index) => {
          if (item.yearMonth.slice(0, 7) === selectedYearAndMonth.slice(0, 7)) {
            return (
              <div key={item.id}>
                {item.id}|{item.yearMonth}|{item.memberId}|{item.totalBudget}|{item.category}|
              </div>
            );
          }
        })}
      </div> */}

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
