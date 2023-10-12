// document.addEventListener('DOMContentLoaded', function() {
//     window.addEventListener('scroll', function() {
//         console.log('Scrolling detected');
//         let navbar = document.getElementById('myNavbar');
//         if (window.scrollY > 50) {
//             navbar.classList.add('shadow');
//         } else {
//             navbar.classList.remove('shadow');
//         }
//     });
// });
//
// /*jQuery*/
// // $(function() {
// $('.profile-item--container').append('<div class="itemBckgr"></div>');
// /*END ---> jQuery*/
//
// let profile = document.getElementsByClassName('profil'),
//     itmBgCss = CSSRulePlugin.getRule('.itemBckgr'),
//     itemBckground = [... document.querySelectorAll('.itemBckgr')],
//     profilItemContainer = [... document.querySelectorAll('.profil-item--container')],
//     profilChilds = [... document.querySelectorAll('.profil-item')],
//     tl = new TimelineMax({paused:true});
//
// tl.staggerFromTo(
//     profilItemContainer, 1, {x: 0, y: -20}, {x: -25, y: 0},0.5, "start")
//     .staggerFromTo(
//         profilItemContainer, 1.5, {scale: 0}, {scale: 1}, 0.2, "start")
//     .to(profil, 1, {
//         transformOrigin: "100px 100px", rotation: -180, ease: Power2.easeIn}, "start+=0.1")
//     .staggerTo(
//         profilChilds, 0.5, {rotation: -180}, 0.2, "start+=0.3")
//     .to(
//         itemBckground, 0.7, {borderRadius: "5%", stagger: 0.2, ease: Power4.easeIn}, "start+=0.15")
//     .to(
//         itemBckground, 1.2, {rotation: 630, stagger: 0.15}, "start+=0.35")
//     .to(
//         itmBgCss, 0.3, {"border-color": "rgba(255,255,255,0)", stagger: 0.2}, "start+=0.7")
//     .to(
//         itmBgCss, 0.6, {"background": "rgba(255,255,255,1)", stagger: 0.4}, "start+=0.8")
//     .to(
//         itemBckground, 0.4, {scaleX: 0.05, stagger: 0.2}, "start+=1.5")
//     .fromTo(
//         profilChilds, 0.1, {alpha: 0}, {alpha: 1, stagger: 0.15}, "start+=1.6")
//     .to(
//         itemBckground, 0.2, {left: "-100%", stagger: 0.15})
//     .to(
//         itemBckground, 0.1, {scaleY: 0, stagger: 0.15}, "start+=2.4");
//
// profil[0].addEventListener('mouseenter', (e) => {
//     tl.restart().timeScale(1.2);
// });
//
// profil[0].addEventListener('mouseleave', (e) => {
//     tl.reverse();
// });