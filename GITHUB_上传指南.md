# 把 Goety Supreme 上传到 GitHub（网页建仓库 + 导入源码）

目标仓库：**`https://github.com/miku-supreme/goety_supreme`**
（Owner：`miku-supreme`；Repository name：`goety_supreme`）

面向已经注册好 GitHub 账号、但没怎么用过 Git 的情况。全程分四步，照着做即可。
仓库协议使用 **MIT**，本工程已经放好 `LICENSE` 文件，版权人已填 `miku-supreme`，不用另外折腾。

> **重要前提**：请使用本工程导出的 **源码包 `goety_supreme_源码包.zip`** 来上传，
> **不要**直接推送平台工作区那份仓库。工作区的 git 历史里已经含有 76MB 的第三方
> 模组 jar 和大量反编译源码，一旦推上去会永久留在公开历史中，事后很难清理干净。

---

## 第 1 步：在 GitHub 网页上建一个空仓库

1. 登录 GitHub（账号 **`miku-supreme`**），点页面**右上角的 `+` 号** → 选择 **`New repository`**。
   （也可以直接打开 <https://github.com/new>。）
2. 填写：

   | 字段 | 怎么填 |
   | --- | --- |
   | **Owner** | `miku-supreme` |
   | **Repository name** | `goety_supreme`（**照抄，用下划线，不要写成 goety-supreme**） |
   | **Description** | 可选，例如 `Goety 扩展模组：镶星仆从 / 使徒 / 凋灵风暴。Minecraft 1.20.1 Forge。` |
   | **Public / Private** | 想公开就选 **Public**；只想自己看选 Private（Private 也完全能用） |

3. **下面三个初始化选项全部不要勾**：
   - ❌ Add a README file
   - ❌ Add .gitignore
   - ❌ Choose a license

   原因：我们本地已经有 `README.md`、`.gitignore`、`LICENSE`，勾了反而会让本地推送时
   出现「历史不一致」的报错，新手很难处理。保持空仓库最省事。

4. 点绿色按钮 **`Create repository`**。
5. 建好后会跳到一个「Quick setup」页面。确认页面上的地址是：

   ```
   https://github.com/miku-supreme/goety_supreme.git
   ```

   **这一步先别关页面**，等下要用到它。

---

## 第 2 步：把源码包下载并解压到电脑上

1. 在本页面的产物下载区，下载 **`goety_supreme_源码包.zip`**。
2. 解压到一个**纯英文、无空格**的目录，例如：
   - Windows：`D:\mc\goety_supreme`
   - macOS：`~/Documents/goety_supreme`

   不建议放在桌面或中文路径下，Git 对中文路径和空格偶尔会出问题。

3. 打开解压后的文件夹，应该能看到这些内容：

   ```
   src/                 源码与资源
   build.gradle         构建配置
   settings.gradle
   gradle.properties
   README.md
   LICENSE              MIT 协议（版权人已填 miku-supreme）
   .gitignore           忽略规则
   GITHUB_上传指南.md   本文件
   ```

   如果看到 `libs/`、`ref/`、`build/` 之类目录，说明解压错了包，请重新下载上面的源码包。

---

## 第 3 步：把解压后的目录上传到 GitHub

下面三种方式**任选一种**。完全的新手推荐 **方式 A（GitHub Desktop）**，不用敲命令、不用配密钥。

### 方式 A：GitHub Desktop（图形界面，最省事，推荐）

1. 到 <https://desktop.github.com/> 下载并安装 **GitHub Desktop**，用它登录 **`miku-supreme`**。
2. 菜单 **File → Add local repository…**。
3. **Local path** 处选择第 2 步解压出来的目录（例如 `D:\mc\goety_supreme`），点 **Add repository**。
4. 因为它还不是一个 Git 仓库，GitHub Desktop 会提示 **"…is not a Git repository. Would you like to create a repository here instead?"**，
   点那里的 **`create a repository`**。
5. 在弹出的窗口里：
   - **Name**：填 `goety_supreme`
   - **Description**：可留空
   - **Local path**：确认是刚才那个目录
   - **Git ignore**、**License**：都选 **None**（我们已自带文件）
   - 点 **`Create repository`**。
6. 回到主界面，左下角 **Summary** 填一句提交说明，例如 `首次提交`，点 **`Commit to main`**。
7. 点顶部 **`Publish repository`**：
   - **Repository name** 确认是 `goety_supreme`
   - **取消勾选 `Keep this code private`**（想要公开的话；想私有就保持勾选）
   - 点 **`Publish repository`**。

   稍等片刻，代码就上传完了。浏览器打开 <https://github.com/miku-supreme/goety_supreme> 就能看到。

### 方式 B：命令行（装过 Git，想用命令）

在解压目录里打开终端 / Git Bash / PowerShell，**逐行复制执行**（地址已经按你的账号填好，不用改）：

```bash
cd D:\mc\goety_supreme            # 换成你的解压目录
git init -b main
git add .
git commit -m "首次提交"
git remote add origin https://github.com/miku-supreme/goety_supreme.git
git push -u origin main
```

推送目标就是 **`https://github.com/miku-supreme/goety_supreme.git`**，与网页上建好的空仓库一致。

**关于第一次推送的密码**：GitHub 现在不接受账号登录密码，需要 **Personal Access Token**：

1. GitHub 网页 → 右上角头像 → **Settings** → 左侧最下方 **Developer settings**
   → **Personal access tokens** → **Tokens (classic)** → **Generate new token (classic)**。
2. Note 随便填，Expiration 选一个期限，勾选 **`repo`** 权限，点最下方 **Generate token**。
3. **立刻复制那串 token**（离开页面就再也看不到了）。
4. 推送时弹出的用户名填 `miku-supreme`，密码处**粘贴这个 token**。

嫌麻烦就用方式 A，GitHub Desktop 会自动处理登录。

### 方式 C：网页拖拽上传（文件少时可用，本工程不推荐）

GitHub 仓库页面 → **`Add file`** → **`Upload files`** → 把解压后的**文件夹整个拖进去** → 底部 **`Commit changes`**。

本工程有约 2200 个文件，网页上传会很慢且容易中途失败，**优先用方式 A**。

---

## 第 4 步：核对仓库信息

上传完成后，打开 <https://github.com/miku-supreme/goety_supreme> 做最后核对：

1. **`LICENSE` 文件**：里面应显示

   ```
   Copyright (c) 2025 miku-supreme
   ```

   已经替你填好了，不用再改。仓库首页右侧会自动出现 **MIT license** 标签。
2. **`README.md`**：仓库首页会直接渲染它，确认能看到「Goety Supreme」标题和内容概览。
3. **`.gitignore`**：应存在。它保证之后你本地的 `libs/`、`ref/`、`build/` 等内容不会被误传。
4. 仓库首页顶部的路径和名字应显示为 **`miku-supreme / goety_supreme`**。

---

## 常见问题

**Q：仓库名到底用下划线还是横线？**
本指南按你选定的 **`goety_supreme`（下划线）** 来写。网页建的仓库名、GitHub Desktop 里的 Name、
以及 `git remote add` 那一行的地址，三处**必须完全一致**，否则推送会报 404。

**Q：能不能直接点 GitHub 的 `Import a repository`？**
那个功能用于把**别处已有的 Git 仓库地址**搬到 GitHub，你没有这样的地址，所以用不上。按上面的步骤走即可。

**Q：为什么不直接推平台工作区那份仓库？**
工作区的 `.git` 已经有 913MB，870 次提交里包含 76MB 的 Goety 模组 jar、`ref/` 下 61MB 的反编译源码。
它们一旦进入公开历史，即使之后删除文件，历史里仍然能被下载到，等于公开传播他人作品。用导出的源码包重新
`git init`，历史干净、体积小，也没有版权风险。

**Q：以后我改了代码，想更新到 GitHub 怎么办？**
- GitHub Desktop：改完文件后它自动列出改动 → 填 Summary → **Commit to main** → 点右上角 **Push origin**。
- 命令行：`git add . && git commit -m "说明" && git push`。

**Q：上传时报错 `file is 76.00 MB; this exceeds GitHub's file size limit of 100.00 MB`？**
说明把 `libs/` 一起传了。检查 `.gitignore` 是否在解压目录里（它是隐藏文件），并确认没有执行过
`git add -f libs`。

**Q：推送时提示 `remote origin already exists`？**
说明之前加过一次远端。先执行 `git remote set-url origin https://github.com/miku-supreme/goety_supreme.git`
把它改成正确地址，再 `git push -u origin main`。

**Q：仓库里的音乐 `.ogg` 文件会不会有版权问题？**
`src` 里带有若干主题音乐。如果它们是你自己创作或已获授权的，可以保留；如果来自其他作品、影视或模组，
公开到自己仓库可能构成侵权，请自行判断后决定是否删除。

**Q：我想让仓库只包含主模组，不要凋灵风暴怎么办？**
`src/witherstorm/` 是独立的子模组目录，删除它并同步移除 `build.gradle` 中与之相关的第二个
`sourceSet`、`processWitherstormResources` 与 `jar` 任务即可。注意这会改动构建配置，删前先备份。
