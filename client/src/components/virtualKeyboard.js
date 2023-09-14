import React, { useRef, useState } from "react";
import Keyboard from "react-simple-keyboard";
import "react-simple-keyboard/build/css/index.css";

const SampleKeyboard = () => {
  const [input, setInput] = useState("");
  const [layout, setLayout] = useState("default");
  const keyboard = useRef();

  const onChange = (input) => {
    setInput(input);
    console.log("Input changed =>", input);
  };

  const handleShift = () => {
    const newLayoutName = layout === "default" ? "shift" : "default";
    console.log("newLayoutName =>", newLayoutName);
    setLayout(newLayoutName);
  };

  const onKeyPress = (button) => {
    console.log("Button pressed =>", button);
    // Shift및 Caps Lock 버튼을 처리하려는 경우
    if (button === "{shift}" || button === "{lock}") handleShift();
  };

  const onChangeInput = (event) => {
    const input = event.target.valut;
    setInput(input);
    keyboard.current.setInput(input);
  };

  return (
    <>
      <header>
        <h1>React Simple Keyboard</h1>
      </header>
      <div className="keyboardContainer">
        <input
          className="keyboardInput"
          value={input}
          placeholder="입력하려면 가상 키보드를 누르세요."
          onChange={onChangeInput}
        />
        <Keyboard
          keyboardRef={(r) => (keyboard.current = r)} 
          layoutName={layout} // 이거 안하면 shift 처리 안된다.
          onChange={onChange}
          onKeyPress={onKeyPress}
        />
      </div>
    </>
  );
};

export default SampleKeyboard;