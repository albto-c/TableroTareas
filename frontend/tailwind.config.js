/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      height : {
        '90vh' : '90vh',
        '70vh' : '70vh'
      }
    },
  },
  plugins: [],
}

