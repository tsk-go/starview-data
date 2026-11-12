const { chromium } = require('playwright');
const fs = require('fs');
(async () => {
  const [,, q, out] = process.argv;
  const b = await chromium.launch({ args: ['--use-gl=angle', '--use-angle=swiftshader', '--enable-unsafe-swiftshader', '--ignore-gpu-blocklist'] });
  const p = await b.newPage({ viewport: { width: 800, height: 600 } });
  p.on('console', m => console.log('console:', m.text()));
  p.on('pageerror', e => console.log('error:', e.message));
  await p.goto('http://127.0.0.1:8765/' + q);
  await p.waitForFunction(() => document.title === 'done', null, { timeout: 600000 });
  const r = await p.evaluate(() => window.result);
  fs.writeFileSync(out, JSON.stringify(r));
  await b.close();
})();
