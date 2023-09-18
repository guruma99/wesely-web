// 미세먼지
if (navigator.geolocation) {
  navigator.geolocation.getCurrentPosition(function (position) {
    let lat = position.coords.latitude;
    let lon = position.coords.longitude;

    fetch(`http://api.openweathermap.org/data/2.5/air_pollution?lat=${lat}&lon=${lon}&appid=aa1d55a2e7e667baebd92b3946d66932&units=metric`)
      .then(response => response.json())
      .then(mi => {
        let pm10 = mi.list[0].components.pm10;

        let great = '../images/great.png';
        let mise = '../images/mise.png';
        let normal = '../images/normal.png';
        let bad = '../images/bad.png';
        let bbad = '../images/bbad.png';

        let imgElement = document.getElementById('weatherImage');

        if (pm10 > 0 && pm10 <= 20) {
          imgElement.src = great;
          document.querySelector('.mise').textContent += '매우좋음';
        } else if (pm10 > 20 && pm10 <= 50) {
          imgElement.src = mise;
          document.querySelector('.mise').textContent += '좋음';
        } else if (pm10 > 50 && pm10 <= 100) {
          imgElement.src = normal;
          document.querySelector('.mise').textContent += '보통';
        } else if (pm10 > 100 && pm10 <= 200) {
          imgElement.src = bad;
          document.querySelector('.mise').textContent += '나쁨';
        } else if (pm10 >= 200) {
          imgElement.src = bbad;
          document.querySelector('.mise').textContent += '매우나쁨';
        }
      })
      .catch(error => console.error('Error:', error));

  }, function (error) { console.error(error); });
} else { console.error("Geolocation is not supported by this browser.") };
// 날씨
if (navigator.geolocation) {
  navigator.geolocation.getCurrentPosition(function (position) {
    let lat = position.coords.latitude;
    let lon = position.coords.longitude;

    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=aa1d55a2e7e667baebd92b3946d66932&units=metric`)
      .then(response => response.json())
      .then(result => {
        let temp = Math.round(result.main.temp);
        let weather = result.weather[0].main;
        let weather1 = result.weather[0].description;

        document.querySelector(".wt").textContent = temp;

        currentTemp = temp;
        // 비오는날
        if (weather === "Rain" || weather === 'Drizzle' || weather === 'Thunderstorm') {
          
          for (let i = 1; i <= 4; i++) {
            var rainDiv = document.createElement('div');
            rainDiv.className = `rain${i}`;
            document.querySelector('.window').appendChild(rainDiv);
          }
          
          var smText = document.createElement('b');
          smText.innerHTML = '비가 내리는 날씨! <br />실내에서 운동할까요?'
          document.querySelector('.sm1').appendChild(smText);
          
          let keyframes = [
            { transform: 'rotate(10deg) translateY(0px)' },
            { transform: 'rotate(10deg) translateY(100px)' }
          ];

          Array.from(document.querySelectorAll('.window div')).forEach((element, index) => {
            let options = {
              duration: 1200 + index * 100,
              iterations: Infinity
            };
          

            element.animate(keyframes, options);
          });

          // 선선한날
        } else if (weather1 === 'few clouds' || weather1 === "scattered clouds") {
          document.querySelector('.inAct img').src = '/images/runAct.png';

          var runImgElement = document.createElement('img');
          runImgElement.className = 'run';
          runImgElement.src = '/images/run.png';
          document.querySelector('.inAct').appendChild(runImgElement);

          document.querySelector('.window').style.display = 'none';

          var smText = document.createElement('b');
          smText.innerHTML = '선선한 날씨! <br />야외활동은 어떠세요?'
          document.querySelector('.sm1').appendChild(smText);

          let keyframes = [
            { transform: 'translateY(0%)' },
            { transform: 'translateY(10%)' },
            { transform: 'translateY(5%)' },
          ];

          let keyframes1 = [
            { transform: 'translateX(0%)' },
            { transform: 'translateX(-100%)' }
          ];

          let options = {
            duration: 800,
            iterations: Infinity
          };

          let options1 = {
            duration: 3000,
            iterations: Infinity
          };

          Array.from(document.querySelectorAll('.inAct img')).forEach((element, index) => {
            if (index === 0) element.animate(keyframes, options);
            else element.animate(keyframes1, options1);

            // Assuming '.window img' exists.
            if (document.querySelector('.window img'))
              document.querySelector('.window img').animate(keyframes1, options1);

          });

    
        // 흐린날
} else if (weather1 === "broken clouds" || weather1 === "overcast clouds") {
  document.querySelector('.inAct img').src='/images/calm.png';
  
  var backGroundImgElement=document.createElement('img');
  backGroundImgElement.className='backGround';
  backGroundImgElement.src='/images/backGround.png';
  document.querySelector('.inAct').appendChild(backGroundImgElement);
  
  var cloudImgElement=document.createElement('img');
  cloudImgElement.className='cloud';
  cloudImgElement.src='/images/cloud.png';	
  document.querySelector('.window').appendChild(cloudImgElement);
  
  var smText=document.createElement('b');
  smText.innerHTML='흐린 날씨! <br />차분한 실내운동은 어떠세요?'
	document.querySelector('.sm1').appendChild(smText);

	let keyFrames = [
		{ transform: 'translateX(0%)' },
		{ transform: 'translateX(-100%)' }
	]
	let options = {
	  duration: 9000,
	  iterations: Infinity
	}
	document.querySelector('.window img').animate(keyFrames, options);


          //화창한날
} else if (weather === "Clear") {
  document.querySelector('.inAct img').src='/images/teni.png';
  
  var ballImgElement=document.createElement('img');
  ballImgElement.className='ball';
  ballImgElement.src='/images/ball.png';
  document.querySelector('.inAct').appendChild(ballImgElement);
  
  document.querySelector('.window').style.display='none';

  var smText=document.createElement('b');
  smText.innerHTML='화창한 날씨! <br />야외활동은 어떠세요?'
	document.querySelector('.sm1').appendChild(smText);

	let keyFrames = [
		{ transform: 'translateX(0%)' },
		{ transform: 'translateX(-100%)' }
	]
	let options = {
	  duration: 1000,
	  iterations: Infinity
	}
	document.querySelector('.ball').animate(keyFrames, options);

        // 나머지 비오는날 = (Snow Mist Smoke Haze Dust Fog Sand Dust Ash Squall Tornado)
} else {
  document.querySelector('.inAct img').src='/images/calm.png';
  
  var backGroundImgElement=document.createElement('img');
  backGroundImgElement.src='/images/backGround.png';
  document.querySelector('.inAct img').appendChild(backGroundImgElement);
  
  var cloudImgElement=document.createElement('img');
  cloudImgElement.className='cloud';
  cloudImgElement.src='/images/cloud.png';	
	document.querySelector('.window').appendChild(cloudImgElement);

	var smText=document.createElement('b');
	smText.innerHTML='흐린 날씨! <br />차분한 실내운동은 어떠세요?'
	document.querySelector('.sm1').appendChild(smText);

	let keyFrames = [
		{ transform: 'translateX(0%)' },
		{ transform: 'translateX(-100%)' }
	]
	let options = {
	  duration: 9000,
	  iterations: Infinity
	}
	document.querySelector('.window img').animate(keyFrames, options);
}

function formatDate(date) {
  let d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2)
    month = '0' + month;
  if (day.length < 2)
    day = '0' + day;

  return [year, month, day].join('-');
}

if (navigator.geolocation) {
  navigator.geolocation.getCurrentPosition(function (position) {
    let lat = position.coords.latitude;
    let lon = position.coords.longitude;

    let now = new Date();
    now.setDate(now.getDate() - 1); // 하루 전으로 시간을 설정
 
    let yesterdayStr = formatDate(now);

    let apiKey ="2399NSNUHN9TFXYSWNKYFCAWG"; // 발급받은 API 키 입력
 
    // Visual Crossing API 호출 부분
    fetch(`https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/${lat},${lon}/${yesterdayStr}?key=${apiKey}&include=obs`)
      .then(response => response.json())
      .then(data => {
        yesterdayTemp = Math.round((data.days[0].temp - 32) * 5 / 9); // 화씨에서 섭씨로 변환
        let diff = currentTemp - yesterdayTemp; // 현재온도와 어제온도 차이 계산

        if (currentTemp > yesterdayTemp) {
          document.querySelector('.YesWeather').innerHTML =
            "어제 평균온도보다" + diff + "°C 높아요";
        } else if (currentTemp < yesterdayTemp) {
          document.querySelector('.YesWeather').innerHTML =
            "어제 평균온도보다 " + Math.abs(diff) + "°C 낮아요";
        }

      })
      .catch(error => console.error('Error:', error));
  })}
})})};


// 슬라이드 기능
// let slides = document.querySelector('.slide ');
// let slideDiv = document.querySelectorAll('.slide div');
// currentIdx = 0;
// slideCount = slideDiv.length;
// LBtn = document.querySelector('.LBtn');
// RBtn = document.querySelector('.RBtn');
// slideWidth = 300;
// slideMargin = 10;
// copy(); // 처음 이미지 마지막이미지 복사 함수
// reset(); // 슬라이드 넓이 위치값 초기화 함수

// function copy() {
//   let cloneSlideFirst = slideDiv[0].cloneNode(true);
//   let cloneSlideLast = slides.lastElementChild.cloneNode(true);
//   slides.append(cloneSlideFirst);
//   slides.insertBefore(cloneSlideLast, slides.firstElementChild);
// }
// function reset() {
//   slides.style.width = (slideWidth + slideMargin) * (slideCount + 1) + 'px';
//   slides.style.left = -(slideWidth + slideMargin) + 'px';
// }

// RBtn.addEventListener('click', function () {
//   //오른쪽 버튼 눌렀을때
//   if (currentIdx <= slideCount - 1) {
//     //슬라이드이동
//     slides.style.left = -(currentIdx + 2) * (slideWidth + slideMargin) + 'px';
//     slides.style.transition = `${0.5}s ease-out`; //이동 속도
//   }
//   if (currentIdx === slideCount - 1) {
//     //마지막 슬라이드 일때
//     setTimeout(function () {
//       //0.5초동안 복사한 첫번째 이미지에서, 진짜 첫번째 위치로 이동
//       slides.style.left = -(slideWidth + slideMargin) + 'px';
//       slides.style.transition = `${0}s ease-out`;
//     }, 500);
//     currentIdx = -1;
//   }
//   currentIdx += 1;
// });
// LBtn.addEventListener('click', function () {
//   //이전 버튼 눌렀을때
//   console.log(currentIdx);
//   if (currentIdx >= 0) {
//     slides.style.left = -currentIdx * (slideWidth + slideMargin) + 'px';
//     slides.style.transition = `${0.5}s ease-out`;
//   }
//   if (currentIdx === 0) {
//     setTimeout(function () {
//       slides.style.left = -slideCount * (slideWidth + slideMargin) + 'px';
//       slides.style.transition = `${0}s ease-out`;
//     }, 500);
//     currentIdx = slideCount;
//   }
//   currentIdx -= 1;
// });
