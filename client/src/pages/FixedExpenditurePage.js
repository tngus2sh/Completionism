import React, { useState } from "react";
import Modal from "react-modal";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import { useSelector } from "react-redux/es/hooks/useSelector";
import "./FixedExpenditurePage.css";
import axios from "axios"; // axios 라이브러리 추가
import { useDispatch } from "react-redux";
import { fatchPinnedData } from "../redux/authSlice";
import { useEffect } from "react";
import EditCalendarRoundedIcon from "@mui/icons-material/EditCalendarRounded";
import MoreVertRoundedIcon from "@mui/icons-material/MoreVertRounded";

import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Fade from "@mui/material/Fade";

const FixedExpenditurePage = () => {
  const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
  const upperNavbarName = `${selectedYearAndMonth.split("-")[0]}년 ${selectedYearAndMonth.split("-")[1]}월 고정지출`;
  const dispatch = useDispatch();
  const [useAxios, setUseAxios] = useState(false);

  const fixedExpenditureList = useSelector((state) => state.auth.fixedExpenditureList);

  // 입력 필드와 연결된 state 변수들
  const [todo, setTodo] = useState("");
  const [cost, setCost] = useState(0);
  const [fixed, setFixed] = useState(true);
  const [plus, setPlus] = useState(false);
  const [periodType, setPeriodType] = useState(false);
  const [period, setPeriod] = useState(1);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [fixedScheduleId, setFixedScheduleId] = useState(-1);

  // useEffect(() => {}, );
  useEffect(() => {}, [fixedScheduleId, periodType]);

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
      const response = await axios.get("/api/schedule/pinned", { headers });
      console.log(response.data);
      dispatch(fatchPinnedData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };

  const createData = async () => {
    setIsModalOpen(false);

    // 데이터를 객체로 만들기
    const data = {
      todo: todo,
      cost: cost,
      fixed: fixed,
      plus: plus,
      periodType: periodType,
      period: period,
    };

    // 로컬 스토리지에서 엑세스 토큰 가져오기 (필요한 경우)
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정 (필요한 경우)
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    try {
      const response = await axios.post("/api/schedule/pinned", data, {
        headers,
      });
      console.log(response);
      setUseAxios(!useAxios);
      // 요청 후 입력 필드 초기화
      setTodo("");
      setCost(0);
      setFixed(true);
      setPlus(false);
      setPeriodType(false);
      setPeriod(1);
    } catch (error) {
      console.error(error);
      console.log(data);
      
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
      const response = await axios.delete(`/api/schedule/pinned/${id}`, {
        headers,
      });
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event, id) => {
    setAnchorEl(event.currentTarget);
    setFixedScheduleId(id);
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

  function setScreenSize() {
    //먼저 뷰포트 높이를 얻고 1%를 곱하여 vh 단위 값을 얻습니다.
    let vh = window.innerHeight * 0.01;
    //그런 다음 --vh 사용자 정의 속성의 값을 문서의 루트로 설정합니다.
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  setScreenSize();
  window.addEventListener("resize", setScreenSize);

  function monthPeriod() {
    let arr = [];
    for (let i = 1; i <= 31; i++) {
      arr.push(<option onClick={() => setPeriod(i)}>{i}</option>);
    }
    return arr;
  }

  function weekPeriod() {
    let week = ["월", "화", "수", "목", "금", "토", "일"];
    let arr = [];
    for (let i = 1; i <= 7; i++) {
      arr.push(<option onClick={() => setPeriod(i)}>{week[i - 1]}</option>);
    }
    return arr;
  }

  function selectOption() {
    console.log(periodType);
  
    const handleSelect = (e) => {
      setPeriod(e.target.value);
    };
  
    if (periodType) {
      return (
        <select className="period-select" value={period} onChange={handleSelect}>
          {monthPeriod()}
        </select>
      );
    } else {
      return (
        <select className="period-select" value={period} onChange={handleSelect}>
          {weekPeriod()}
        </select>
      );
    }
  }

  return (
    <div className="fixed-page">
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar" />

      {/* <div>
        <div>
          <label>Todo:</label>
          <input type="text" value={todo} onChange={(e) => setTodo(e.target.value)} />
        </div>
        <div>
          <label>Cost:</label>
          <input type="number" value={cost} onChange={(e) => setCost(parseFloat(e.target.value))} />
        </div>
        <div>
          <label>Plus:</label>
          <input type="checkbox" checked={plus} onChange={(e) => setPlus(e.target.checked)} />
        </div>
        <div>
          <label>Period Type(체크하면 월 지출):</label>
          <input type="checkbox" checked={periodType} onChange={(e) => setPeriodType(e.target.checked)} />
        </div>
        <div>
          <label>Period:</label>
          <input type="number" value={period} onChange={(e) => setPeriod(parseInt(e.target.value))} />
        </div>
        <button onClick={createData}>고정지출 생성하기</button>
        <hr />
      </div> */}

      <div className="fixed-info-container">
        <div className="balloon">
          <span>
            💡 고정적으로 나가는 지출을 작성해서
            <br />
            현명하게 소비해요!
          </span>
        </div>
      </div>

      <div className="fixed-button-size-container">
        <div className="fixed-button-container">
          <span>
            <strong>작성하기&nbsp;</strong>
          </span>
          <div onClick={openModal} className="fixed-button-icon-container">
            <div className="fixed-button-icon-flex-container">
              <EditCalendarRoundedIcon sx={{ color: "#0046FF" }} />
            </div>
          </div>
        </div>
      </div>

      <div>
        {fixedExpenditureList.map((item, index) => {
          const handleClose = () => {
            setAnchorEl(null);
          };

          return (
            <div className="fixed-item-container">
              <div className="fixed-item-flex-container">
                <div className="fixed-item-main-container">
                  <div className="fixed-item-main-flex-container">
                    <div>{item.todo}</div>
                    <div style={{ fontSize: "0.85rem", color: "#696969" }}>
                      {item.date.split("-")[0]}년 {item.date.split("-")[1]}월 {item.date.split("-")[2]}일
                    </div>
                  </div>
                </div>

                <div className="fixed-item-cost-container">{item.cost}원</div>

                <div className="fixed-item-info-container">
                  <Button
                    id="fade-button"
                    aria-controls={open ? "fade-menu" : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? "true" : undefined}
                    onClick={(e) => {
                      handleClick(e, item.id);
                    }}
                  >
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
                    <MenuItem
                      onClick={() => {
                        deleteData(fixedScheduleId);
                        setUseAxios(!useAxios);
                      }}
                      sx={{ padding: "0 0.7rem" }}
                    >
                      삭제
                    </MenuItem>
                  </Menu>
                </div>
              </div>
            </div>

            // <div>
            //   id: {item.id}|{item.date}|{item.todo}|{item.cost}|{item.plus}
            //   <button
            //     onClick={() => {
            //       deleteData(item.id);
            //       setUseAxios(!useAxios);
            //     }}
            //   >
            //     삭제
            //   </button>
            //   <hr />
            // </div>
          );
        })}
      </div>

      <Modal
        isOpen={isModalOpen}
        onRequestClose={() => setIsModalOpen(false)} // 모달 닫기
        style={modalStyle}
        contentLabel="고정 지출 등록 모달"
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <div style={{ display: "inline-block", width: "90%", height: "100%" }}>
          <div className="fixed-header-container" style={{ marginTop: "2.5rem" }}>
            <h3>고정 지출 등록</h3>
          </div>

          <div className="fixed-date-container">
            {/* <div className="fixed-create-flex-container">
              <div className="fixed-create-info-container">날짜</div>
              <div className="fixed-create-content-container">
                <div className="fixed-create-border-container">
                  <div className="fixed-create-border-flex-container">
                    <DatePicker locale="ko" selected={startDate} onChange={(date) => setStartDate(date)} dateFormat="yyyy-MM-dd" showPopperArrow={false} fixedHeight className="datepicker" />
                  </div>
                </div>
              </div>
            </div> */}

            <div className="fixed-create-flex-container">
              <div className="fixed-create-info-container">내용</div>
              <div className="fixed-create-content-container">
                <div className="fixed-create-border-container">
                  <div className="fixed-create-border-flex-container">
                    <input type="text" value={todo} onChange={(e) => setTodo(e.target.value)} />
                  </div>
                </div>
              </div>
            </div>

            <div className="fixed-create-flex-container">
              <div className="fixed-create-info-container">금액</div>
              <div className="fixed-create-content-container">
                <div className="fixed-create-border-container">
                  <div className="fixed-create-border-flex-container">
                    <input type="number" value={cost} onChange={(e) => setCost(parseFloat(e.target.value))} />
                  </div>
                </div>
              </div>
            </div>

            <div className="fixed-create-flex-container">
              <div className="fixed-create-info-container">주기</div>
              <div className="fixed-create-content-container">
                <div className="fixed-create-border-radio-container">
                  <div className="fixed-create-border-radio-flex-container">
                    <div class="select">
                      <div onClick={(e) => setPeriodType(0)}>
                        <input type="radio" id="select" name="shop" checked />
                        <label for="select">주마다</label>
                      </div>
                      <div onClick={(e) => setPeriodType(1)}>
                        <input type="radio" id="select2" name="shop" />
                        <label for="select2">월마다</label>
                      </div>
                    </div>

                    {/* <input type="checkbox" checked={periodType} onChange={(e) => setPeriodType(e.target.checked)} /> */}
                  </div>
                </div>
              </div>
            </div>

            <div className="fixed-create-flex-container">
              <div className="fixed-create-info-container">기간</div>
              <div className="fixed-create-content-container">
                <div className="fixed-create-border-container">
                  <div className="fixed-create-border-flex-container">
                    {selectOption()}

                    {/* <input type="checkbox" checked={periodType} onChange={(e) => setPeriodType(e.target.checked)} /> */}
                  </div>
                </div>
              </div>

              {/* <div className="fixed-create-flex-container"></div> */}
            </div>
          </div>

          {/* <div>
            <label>Plus(체크하면 수입):</label>
            <input type="checkbox" checked={plus} onChange={(e) => setPlus(e.target.checked)} />
          </div> */}

          <div className="fixed-create-button-container">
            <div onClick={createData} className="fixed-create-button-flex-container">
              생성하기
            </div>
          </div>
        </div>
      </Modal>

      <div
        style={{
          display: "inline-block",
          width: "100%",
          height: "6rem",
          backgroundColor: "#F0F1F4",
        }}
      ></div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default FixedExpenditurePage;
