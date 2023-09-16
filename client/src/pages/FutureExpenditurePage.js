import React, { useState } from "react";
import Modal from "react-modal";
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
import EditCalendarRoundedIcon from "@mui/icons-material/EditCalendarRounded";
import MoreVertRoundedIcon from "@mui/icons-material/MoreVertRounded";

import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Fade from "@mui/material/Fade";

const FutureExpenditurePage = () => {
  const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
  console.log(selectedYearAndMonth);
  const upperNavbarName = `${selectedYearAndMonth.split("-")[0]}년 ${selectedYearAndMonth.split("-")[1]}월 미래소비`;
  const FutureExpenditureList = useSelector((state) => state.auth.FutureExpenditureList);
  const dispatch = useDispatch();

  const [useAxios, setUseAxios] = useState(false);
  // 입력 필드와 연결된 state 변수들
  const [todo, setTodo] = useState("");
  const [cost, setCost] = useState(0);
  const [plus, setPlus] = useState(false);
  const [fixed, setFixed] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  //데이트 피커 전용 변수
  const [startDate, setStartDate] = useState(new Date());
  // const ExampleCustomInput = ({ value, onClick }) => (
  //   <button className="example-custom-input" onClick={onClick}>
  //     {value}
  //   </button>
  // );

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
    setIsModalOpen(false);

    // 데이터를 객체로 만들기
    const data = {
      date: startDate.getFullYear() + "-" + (startDate.getMonth() + 1).toString().padStart(2, "0") + "-" + startDate.getDate().toString().padStart(2, "0"),
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
      console.log(data);
    }
  };

  const deleteData = async (id) => {
    console.log("id: ", id);
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

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

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
      boxShadow: 20,
      textAlign: "center",
      overflowY: "auto", // 스크롤바 추가
      outline: "none",
      backdropFilter: "blur(5px)",
      overlay: {
        background: "gray",
      },
      // bgcolor: "white",
    },
  };

  function setScreenSize() {
    //먼저 뷰포트 높이를 얻고 1%를 곱하여 vh 단위 값을 얻습니다.
    let vh = window.innerHeight * 0.01;
    //그런 다음 --vh 사용자 정의 속성의 값을 문서의 루트로 설정합니다.
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  setScreenSize();
  window.addEventListener("resize", setScreenSize);

  return (
    <div className="future-page">
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar" />

      <div className="future-info-container">
        <div className="balloon">
          <span>
            💡 이번 달에 예상되는 지출을 미리 작성해서
            <br />
            미래에 대비하세요!
          </span>
        </div>
      </div>

      <div className="future-button-size-container">
        <div className="future-button-container">
          <span>
            <strong>작성하기&nbsp;</strong>
          </span>
          <div onClick={openModal} className="future-button-icon-container">
            <div className="future-button-icon-flex-container">
              <EditCalendarRoundedIcon sx={{ color: "#0046FF" }} />
            </div>
          </div>
        </div>
      </div>

      <div>
        {FutureExpenditureList.map((item, index) => {
          const handleClose = () => {
            setAnchorEl(null);
          };

          const deleteFutureItem = () => {
            deleteData(item.id);
            setUseAxios(!useAxios);
          };

          if (item.date.slice(0, 7) === selectedYearAndMonth.slice(0, 7)) {
            return (
              <div className="future-item-container">
                <div className="future-item-flex-container">
                  <div className="future-item-main-container">
                    <div className="future-item-main-flex-container">
                      <div>{item.todo}</div>
                      <div style={{ fontSize: "0.85rem", color: "#696969" }}>
                        {item.date.split("-")[0]}년 {item.date.split("-")[1]}월 {item.date.split("-")[2]}일
                      </div>
                    </div>
                  </div>

                  <div className="future-item-cost-container">{item.cost}원</div>

                  <div className="future-item-info-container">
                    <Button id="fade-button" aria-controls={open ? "fade-menu" : undefined} aria-haspopup="true" aria-expanded={open ? "true" : undefined} onClick={handleClick}>
                      <MoreVertRoundedIcon sx={{ color: "#696969" }} />
                    </Button>
                    <Menu
                      id="fade-menu"
                      MenuListProps={{
                        "aria-labelledby": "fade-button",
                      }}
                      anchorEl={anchorEl}
                      open={open}
                      onClose={handleClose}
                      TransitionComponent={Fade}
                    >
                      {/* <MenuItem onClick={deleteFutureItem}>삭제</MenuItem> */}
                      <MenuItem
                        onClick={() => {
                          deleteData(item.id);
                          setUseAxios(!useAxios);
                        }}
                      >
                        삭제
                      </MenuItem>
                    </Menu>
                  </div>
                </div>
                {/* <div className="test-container"></div>
                id: {item.id}|{item.date}|{item.todo}|{item.cost}|{item.plus}
                <button
                  onClick={() => {
                    deleteData(item.id);
                    setUseAxios(!useAxios);
                  }}
                >
                  삭제
                </button> */}
              </div>
            );
          }
        })}
      </div>

      <Modal
        isOpen={isModalOpen}
        onRequestClose={() => setIsModalOpen(false)} // 모달 닫기
        style={modalStyle}
        contentLabel="미래 소비 등록 모달"
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <div style={{ display: "inline-block", width: "90%", height: "100%" }}>
          <div className="future-header-container" style={{ marginTop: "2.5rem" }}>
            <h3>미래 소비 등록</h3>
          </div>

          <div className="future-date-container">
            <div className="future-create-flex-container">
              <div className="future-create-info-container">날짜</div>
              <div className="future-create-content-container">
                {/* <DatePicker selected={startDate} onChange={(date) => setStartDate(date)} customInput={<ExampleCustomInput />} dateFormat="yyyy-MM-dd" className="datepicker" /> */}
                <div className="future-create-border-container">
                  <div className="future-create-border-flex-container">
                    <DatePicker locale="ko" selected={startDate} onChange={(date) => setStartDate(date)} dateFormat="yyyy-MM-dd" showPopperArrow={false} fixedHeight className="datepicker" />
                  </div>
                </div>
              </div>
            </div>

            <div className="future-create-flex-container">
              <div className="future-create-info-container">내용</div>
              <div className="future-create-content-container">
                <div className="future-create-border-container">
                  <div className="future-create-border-flex-container">
                    <input type="text" value={todo} onChange={(e) => setTodo(e.target.value)} />
                  </div>
                </div>
              </div>
            </div>

            <div className="future-create-flex-container">
              <div className="future-create-info-container">비용</div>
              <div className="future-create-content-container">
                <div className="future-create-border-container">
                  <div className="future-create-border-flex-container">
                    <input type="number" value={cost} onChange={(e) => setCost(parseFloat(e.target.value))} />
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* <div>
            <label>Plus(체크하면 수입):</label>
            <input type="checkbox" checked={plus} onChange={(e) => setPlus(e.target.checked)} />
          </div> */}

          <div className="future-create-button-container">
            <div onClick={createData} className="future-create-button-flex-container">
              생성하기
            </div>
          </div>
        </div>
      </Modal>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default FutureExpenditurePage;
