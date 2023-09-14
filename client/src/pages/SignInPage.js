import { Grid, TextField, Button } from "@mui/material";
import React, { useState } from "react";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./SignInPage.css";
import { Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginState } from "../redux/authSlice";
import { useDispatch } from "react-redux";

const SignInPage = () => {
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated); // state.user는 Redux 스토어의 user 상태를 나타냅니다.
  // console.log(isAuthenticated)
  const navigate = useNavigate();
  const upperNavbarName = "로그인";
  const [formData, setFormData] = useState({
    loginId: "",
    loginPwd: "",
  });

  // accessToken 상태 추가
  const [accessToken, setAccessToken] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("/api/auth/login", formData);

      console.log(response.data);
      console.log(response.data.dataBody.accessToken);

      // accessToken을 localStorage에 저장
      localStorage.setItem("accessToken", response.data.dataBody.accessToken);

      // accessToken 상태 업데이트
      setAccessToken(response.data.dataBody.accessToken);
      navigate("/mainpage");
      dispatch(loginState());

      setFormData({
        loginId: "",
        loginPwd: "",
      });
    } catch (error) {
      console.error(error);
    }
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
    <div style={{ display: "inline-block", height: "calc(var(--vh, 1vh) * 100)", backgroundColor: "#F9F9FC" }}>
      <Grid container spacing={2} direction="row" justifyContent="center" alignItems="center">
        <Grid item xs={12}>
          <UpperNavigationBar props={upperNavbarName} />
        </Grid>
        <Grid className="progressive_bar" sx={{ marginBottom: "6.5rem" }}></Grid>
        <Grid item xs={10}>
          <form onSubmit={handleSubmit}>
            <Grid container columnSpacing={2} justifyContent="center" alignItems="center">
              <div style={{ width: "100%", border: "1px solid #919191", borderRadius: "1rem", backgroundColor: "white", marginBottom: "2rem" }}>
                <Grid item xs={12}>
                  <TextField
                    type="text"
                    label="아이디"
                    variant="standard"
                    fullWidth
                    InputProps={{ disableUnderline: true, sx: { height: "3rem", padding: "0 1rem" } }}
                    InputLabelProps={{ sx: { textIndent: "1.5rem" } }}
                    value={formData.loginId}
                    onChange={(e) => setFormData({ ...formData, loginId: e.target.value })}
                  />
                </Grid>
                <hr style={{ margin: 0 }} />
                <Grid item xs={12}>
                  <TextField
                    type="password"
                    label="비밀번호"
                    variant="standard"
                    fullWidth
                    InputProps={{ disableUnderline: true, sx: { height: "3rem", padding: "0 1rem" } }}
                    InputLabelProps={{ sx: { textIndent: "1.5rem" } }}
                    value={formData.loginPwd}
                    onChange={(e) => setFormData({ ...formData, loginPwd: e.target.value })}
                  />
                </Grid>
              </div>
              <div style={{ width: "100%", marginBottom: "2rem" }}>
                <Grid item xs={12}>
                  <Button type="submit" variant="contained" fullWidth sx={{ height: "3.5rem", borderRadius: "1rem", backgroundColor: "#0046FF", ":focus": { backgroundColor: "#0046FF" } }}>
                    <strong>로그인</strong>
                  </Button>
                </Grid>
              </div>
              <div style={{ width: "100%", marginBottom: "1rem" }}>
                <Grid item xs={12}>
                  <Button variant="outlined" fullWidth sx={{ height: "3.5rem", borderRadius: "1rem", border: "1px solid #0046FF", backgroundColor: "#FFFFFF" }}>
                    <Link to="/signup" style={{ textDecoration: "none", color: "#0046FF", width: "100%", height: "100%", display: "flex", justifyContent: "center", alignItems: "center" }}>
                      <strong>회원가입</strong>
                    </Link>
                  </Button>
                </Grid>
              </div>
              <div style={{ width: "100%", display: "flex", justifyContent: "center" }}>
                <Grid item xs={5}>
                  <Button variant="text" fullWidth sx={{ color: "#919191" }}>
                    아이디 찾기
                  </Button>
                </Grid>
                <Grid item xs={5}>
                  <Button variant="text" fullWidth sx={{ color: "#919191" }}>
                    비밀번호 찾기
                  </Button>
                </Grid>
              </div>
            </Grid>
          </form>
        </Grid>
      </Grid>
    </div>
  );
};

export default SignInPage;
