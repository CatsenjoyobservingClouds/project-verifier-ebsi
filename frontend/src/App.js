import './App.css';
import NavBar from "./components/NavBar";
import QRCodeScanner from "./pages/QRCodeScanner"


function App() {
  return (
    <div className="App">
      <NavBar />
      <QRCodeScanner />
    </div>
  );
}

export default App;
